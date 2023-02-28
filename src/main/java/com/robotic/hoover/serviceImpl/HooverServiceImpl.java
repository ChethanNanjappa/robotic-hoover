package com.robotic.hoover.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotic.hoover.model.Hoover;
import com.robotic.hoover.service.HooverService;

@Service
public class HooverServiceImpl implements HooverService {

	public String roboticHoover(int[] roomDimensions, int[] hooverPosition, List<Character> drivingInstructions,
			List<int[]> dirtLocations) {
		
		int[] finalPosition = new int[] { hooverPosition[0], hooverPosition[1] };
		int numDirtCleaned = 0;
		Set<String> dirtSet = new HashSet<>();
		for (int[] dirtLocation : dirtLocations) {
			dirtSet.add(dirtLocation[0] + "," + dirtLocation[1]);
		}

		for (char instruction : drivingInstructions) {
			int[] newPosition = new int[] { finalPosition[0], finalPosition[1] };
			switch (instruction) {
			case 'N':
				newPosition[1] += 1;
				break;
			case 'S':
				newPosition[1] -= 1;
				break;
			case 'E':
				newPosition[0] += 1;
				break;
			case 'W':
				newPosition[0] -= 1;
				break;
			default:
				continue;
			}

			// Check if the new position is within the room boundaries
			if (newPosition[0] < 0 || newPosition[0] >= roomDimensions[0] || newPosition[1] < 0
					|| newPosition[1] >= roomDimensions[1]) {
				continue; // Hoover hit a wall, do not move
			}

			// Check if the new position has dirt
			if (dirtSet.contains(newPosition[0] + "," + newPosition[1])) {
				dirtSet.remove(newPosition[0] + "," + newPosition[1]); // Clean the dirt
				numDirtCleaned++;
			}

			// Move the hoover to the new position
			finalPosition = newPosition;
		}
		return prepareOutput(finalPosition, numDirtCleaned);
	}

	public Hoover JsonParser(String jsonData) {
		Hoover hoover = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonData);

			int[] roomSize = objectMapper.convertValue(jsonNode.get("roomSize"), int[].class);
			int[] coords = objectMapper.convertValue(jsonNode.get("coords"), int[].class);
			int[][] patches = objectMapper.convertValue(jsonNode.get("patches"), int[][].class);

			List<int[]> patchesList = Arrays.stream(patches).map(row -> Arrays.stream(row).toArray())
					.collect(Collectors.toList());

			char[] charArray = jsonNode.get("instructions").asText().toCharArray();
			List<Character> instructions = new ArrayList<>();
			for (char c : charArray) {
				instructions.add(c);
			}
			hoover = new Hoover(roomSize, coords, patchesList, instructions);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return hoover;
	}

	private String prepareOutput(int[] finalPosition, int numDirtCleaned) {
		Map<String, Object> result = new HashMap<>();
		ArrayList<Integer> coords = new ArrayList<>();

		for (int finalPositionElement : finalPosition) {
			coords.add(finalPositionElement);
		}
		result.put("patches", numDirtCleaned);
		result.put("coords", coords);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Error processing JSON";
		}
	}
}
