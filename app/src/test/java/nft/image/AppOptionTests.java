package nft.image;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AppOptionTests {

  @Test
  public void 탭구분자_dto_로변환() throws IOException {
    List<String> list = new ArrayList<>();
    list.add("pnu1234^서울특별시 강남구^100");
    list.add("pnu1234^서울특별시 강북구^200");

    AppOption appOption = new AppOption();
    List<DataDto> dtos = appOption.toDataDtos(list);

    System.out.println(dtos);
    assertTrue(2 == dtos.size());

  }
}
