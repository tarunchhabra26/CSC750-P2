package com.tarun.csc750;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class Router {

	public static final String DEFAULT_INPUT = "input/Time";
	public static final String DEFAULT_QUERY = "Part 4/query.rq";
	public static final String DEFAULT_MODEL = "Part 4/composition.json-ld";

	private Model oModel;
	private String input;

	public Router(String inputMessageName) {

		System.out.println("inputMessageName:" + inputMessageName);
		if (inputMessageName == null || inputMessageName.trim().equals(""))
			this.input = DEFAULT_INPUT;
		else
			this.input = inputMessageName;
		initModel(DEFAULT_MODEL);
		loadRunQuery(DEFAULT_QUERY);
	}

	private void loadRunQuery(String query) {
		String queryString = loadQueryFromFile(query).replace("inputMessage", input);

		System.out.println("Executing Query -> \n" + queryString);

		Query oQuery = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(oQuery, oModel);
		ResultSet results = qe.execSelect();

		try {
			if (results.hasNext()) {
				ResultSetFormatter.out(System.out, results, oQuery);
			} else {
				System.out.println("Invalid Composition");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qe.close();
		}
	}

	private String loadQueryFromFile(String query) {
		return getStringFromFile(query);
	}

	private void initModel(String model) {
		oModel = ModelFactory.createDefaultModel();
		oModel.read(model, "JSON-LD");
	}

	public static void main(String[] args) {
		System.out.println("Input given " + Arrays.toString(args));
		if (args.length == 1) {
			new Router(args[0]);
		} else {
			new Router(null);
		}
	}

	private String getStringFromFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
