/** Program Description: Methods and queries built up for Question table
 *  Author			   : SMNetserv
 *  Date of Creation   : 22/02/2018
**/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class Question {
	public String questionTable;
	public String question;
	public String questionId;
	DBFrameworkConnection connection;
	QueryBuilder build;

	public Question(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Question");
		questionTable = properties.getProperty("db_QuestionTable");
		question = properties.getProperty("db_Questions");
		questionId = properties.getProperty("db_QuestionId");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(questionTable);
	}

	public List<String> getQuestions(List<Integer> questionid) {
		String quesIDStr = "";
		for (int i = 0; i < questionid.size() - 1; i++) {
			quesIDStr += questionid.get(i) + ",";
		}
		quesIDStr += questionid.get(questionid.size() - 1);
		List<String> outFields = new ArrayList<>();
		outFields.add(question);
		build.outFields(outFields);
		build.whereBy(questionId + " in (" + quesIDStr + ")");
		List<Map<String, Object>> questionsData = build.execute(60);
		List<String> questions = new ArrayList<>();
		for (int i = 0; i < questionsData.size(); i++) {
			questions.add((String) questionsData.get(i).get("question"));
		}
		return questions;
	}

	public int questions(String questions) {
		List<String> outFields = new ArrayList<>();
		outFields.add(questionId);
		build.outFields(outFields);
		build.whereBy(question + "= '" + questions + "'");
		List<Map<String, Object>> questionsData = build.execute(60);
		return (int) questionsData.get(0).get("questionId");
	}

	public String question(String questionid) {
		return (String) build.fetch(question, questionTable, questionId + "= '" + questionid + "'");
	}
}
