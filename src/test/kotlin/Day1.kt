import day1.run2
import org.junit.Test



class Day1 {
    @Test
    fun testCase1(){
        day1.run(12) eq 2
    }
    @Test
    fun `14`(){
        day1.run(14) eq 2
    }
    @Test
    fun `1949`(){
        day1.run(1969) eq 654
    }
    @Test
    fun big(){
        day1.run(100756) eq 33583
    }
    
    @Test
    fun `part two 1`(){
        run2(14) eq 2
    }
    @Test
    fun `part two 2`(){
        run2(1969) eq 966
    }
}