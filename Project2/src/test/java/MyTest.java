import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Random;

class MyTest {


	ArrayList<Integer> drawingsSelected;
	Random randomNums;

	@Test
	public void testAddToDrawingsSelected()
	{
		int temp;
		randomNums = new Random();
		drawingsSelected.clear();

		/*Continues to add numbers to the array list until there are 20 with no duplicates*/
		while(drawingsSelected.size() < 20){
			temp = randomNums.nextInt(80) + 1;
			if(!drawingsSelected.contains(temp)){
				drawingsSelected.add(temp);
			}
		}

		assertEquals(20,drawingsSelected.size());

	}

}
