import org.junit.jupiter.api.Assertions.*

class WallServiceTest{
    @Test
    fun testIdNotZero() {
        // arrange
        val service = WallService
        //act
        val result = service.add(Post(1,1,2,2,1205202,"Занятие перенесли",1,1,false,
            14,"text", 14, null,null,null,null,null,1,null,true,true,
            true,true,true,true,))
        //assert
        assertTrue(result.idPost > 0)
    }
}