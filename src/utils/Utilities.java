package utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.jena.atlas.json.JSON;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class Utilities {
	
	public static <T> T readObjectfromFile(final String fileName, Class<T> classType) {
		return readObjectFromString(getStringFromFile(fileName), classType);
	}
	
	public static <T> T readObjectFromString(String string, Class<T> classType) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(string, classType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> String convertObjectToJSONSchema(T object) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
//			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,true);
//			mapper.writeValue(byteArrayOutputStream, object);
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Works
	public static String getStringFromFile(String fileName) {
		StringBuilder sb  = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	sb.append(line);
		    }
		    return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStringFromFileWithNewline(String fileName) {
		StringBuilder sb  = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	sb.append(line+"\n");
		    }
		    return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static void main(String[] args) {
//		System.out.println(getStringFromFile("part1Files/part1-messages.json"));
//		System.out.println(Arrays.toString(readObjectfromFile("part1Files/part1-messages.json", MessageFlat[].class)));
//		System.out.println(readObjectFromString("{\"name\":\"xyzName\", \"id\":\"xyzID\"}", MessageFlat.class));
//		
//		System.out.println(Arrays.toString(readObjectfromFile("part1Files/part1-components.json", ComponentFlat[].class)));
//		System.out.println(Arrays.toString(readObjectfromFile("part1Files/part1-composition.json", CompositionFlat[].class)));
//		System.out.println(Arrays.toString(readObjectfromFile("part2Files/part2-composition.json", CompositionFlat[].class)));
	}
}
