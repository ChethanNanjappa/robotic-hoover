package com.robotic.hoover.serviceImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.robotic.hoover.model.Hoover;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HooverServiceImplTest {

	@Autowired
	HooverServiceImpl hooverServiceImpl;
	
	@Test
	public void testRobotichHoover_basic() {
	    int[] roomDimensions = new int[]{5,5};
	    int[] hooverPosition = new int[]{0,0};
	    List<Character> drivingInstructions = Arrays.asList('N');
	    List<int[]> dirtLocations = new ArrayList<>();
	    String expectedOutput = "{\"patches\":0,\"coords\":[0,1]}";
	    String actualOutput = hooverServiceImpl.roboticHoover(roomDimensions, hooverPosition, drivingInstructions, dirtLocations);
	    assertEquals(expectedOutput, actualOutput);
	}


	@Test
	public void testRobotichHoover_encountersWall() {
	    int[] roomDimensions = new int[]{5,5};
	    int[] hooverPosition = new int[]{0,0};
	    List<Character> drivingInstructions = Arrays.asList('W');
	    List<int[]> dirtLocations = new ArrayList<>();
	    String expectedOutput = "{\"patches\":0,\"coords\":[0,0]}";
	    String actualOutput = hooverServiceImpl.roboticHoover(roomDimensions, hooverPosition, drivingInstructions, dirtLocations);
	    assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void testRobotichHoover_cleansDirt() {
	    int[] roomDimensions = new int[]{5,5};
	    int[] hooverPosition = new int[]{0,0};
	    List<Character> drivingInstructions = Arrays.asList('N', 'E');
	    List<int[]> dirtLocations = Arrays.asList(new int[]{1,0});
	    String expectedOutput = "{\"patches\":0,\"coords\":[1,1]}";
	    String actualOutput = hooverServiceImpl.roboticHoover(roomDimensions, hooverPosition, drivingInstructions, dirtLocations);
	    assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void testRobotichHoover_multipleDirt() {
	    int[] roomDimensions = new int[]{5,5};
	    int[] hooverPosition = new int[]{0,0};
	    List<Character> drivingInstructions = Arrays.asList('N', 'E', 'S', 'W', 'W', 'S', 'E');
	    List<int[]> dirtLocations = Arrays.asList(new int[]{1,0}, new int[]{2,1}, new int[]{3,3});
	    String expectedOutput = "{\"patches\":1,\"coords\":[1,0]}";
	    String actualOutput = hooverServiceImpl.roboticHoover(roomDimensions, hooverPosition, drivingInstructions, dirtLocations);
	    assertEquals(expectedOutput, actualOutput);
	}

	
	@Test
	public void testJsonParserWithValidData() throws Exception{
		String jsonData = "{\"roomSize\": [5, 5], \"coords\": [1, 2], \"patches\": [[1, 0], [2, 2], [2, 3]], \"instructions\": \"NNESEESWNWW\"}";
		Hoover hoover = hooverServiceImpl.JsonParser(jsonData);
		assertNotNull(hoover);
	}

	@Test
	public void testJsonParserWithInvalidData() {
		String jsonData = "{invalidJsonData}";
		Hoover hoover = hooverServiceImpl.JsonParser(jsonData);
		assertNull(hoover);
	}

	@Test
	public void testJsonParserRoomSize() {
		String jsonData = "{\"roomSize\": [5, 5], \"coords\": [1, 2], \"patches\": [[1, 0], [2, 2], [2, 3]], \"instructions\": \"NNESEESWNWW\"}";
		Hoover hoover = hooverServiceImpl.JsonParser(jsonData);
		assertArrayEquals(new int[] { 5, 5 }, hoover.getRoomSize());
	}

	@Test
	public void testJsonParserCoords() {
		String jsonData = "{\"roomSize\": [5, 5], \"coords\": [1, 2], \"patches\": [[1, 0], [2, 2], [2, 3]], \"instructions\": \"NNESEESWNWW\"}";
		Hoover hoover = hooverServiceImpl.JsonParser(jsonData);
		assertArrayEquals(new int[] { 1, 2 }, hoover.getCoords());
	}

	@Test
	public void testJsonParserPatches() {
		String jsonData = "{\"roomSize\": [5, 5], \"coords\": [1, 2], \"patches\": [[1, 0], [2, 2], [2, 3]], \"instructions\": \"NNESEESWNWW\"}";
		Hoover hoover = hooverServiceImpl.JsonParser(jsonData);
		List<int[]> patchesList = hoover.getPatches();
		assertEquals(3, patchesList.size());
		assertArrayEquals(new int[] { 1, 0 }, patchesList.get(0));
		assertArrayEquals(new int[] { 2, 2 }, patchesList.get(1));
		assertArrayEquals(new int[] { 2, 3 }, patchesList.get(2));
	}

	@Test
	public void testJsonParserInstructions() {
		String jsonData = "{\"roomSize\": [5, 5], \"coords\": [1, 2], \"patches\": [[1, 0], [2, 2], [2, 3]], \"instructions\": \"NNESEESWNWW\"}";
		Hoover hoover = hooverServiceImpl.JsonParser(jsonData);
		List<Character> instructions = hoover.getInstructions();
		assertEquals(11, instructions.size());
		assertEquals('N', instructions.get(0).charValue());
		assertEquals('W', instructions.get(10).charValue());
	}

}
