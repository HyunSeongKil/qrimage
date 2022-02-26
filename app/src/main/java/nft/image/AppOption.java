package nft.image;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nft
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppOption {
  private Path outPath;
  private Path subImagePath;
  private Integer subImagePosX;
  private Integer subImagePosY;
  private File basicImageFile;
  private File dataFile;
  private List<DataDto> dataDtos;
  private Long onColor = Long.parseLong("00000000", 16);
  private Long offColor = Long.parseLong("ff000000", 16);
  private MatrixToImageConfig config;
  private Integer qrimageWidth = 100;
  private Integer qrimageHeight = 100;
  private Integer qrimagePosX = 100;
  private Integer qrimagePosY = 100;

  private String fontName = "맑은고딕";
  private Integer fontSize = 20;
  private Integer addressDrawPosX = 100;
  private Integer addressDrawPosY = 200;
  private Color fontColor = new Color(0, 0, 0);

  /**
   * 
   * @param prop
   * @throws IOException
   */
  public AppOption(Properties prop) throws IOException {
    if (null != prop.getProperty("data.file")) {
      this.dataFile = new File(prop.getProperty("data.file"));
      List<String> datas = Files.readAllLines(this.dataFile.toPath()).stream()
          .filter(predicate -> !predicate.startsWith("#"))
          .collect(Collectors.toList());

      this.dataDtos = toDataDtos(datas);
    }

    if (null != prop.getProperty("basic.image.file")) {
      this.basicImageFile = new File(prop.getProperty("basic.image.file"));
    }

    if (null != prop.getProperty("out.path")) {
      this.outPath = Paths.get(prop.getProperty("out.path"));
    }

    if (null != prop.getProperty("sub.image.path")) {
      this.subImagePath = Paths.get(prop.getProperty("sub.image.path"));
    }

    if (null != prop.getProperty("sub.image.pos.x")) {
      this.subImagePosX = Integer.parseInt(prop.getProperty("sub.image.pos.x"));
    }

    if (null != prop.getProperty("sub.image.pos.y")) {
      this.subImagePosY = Integer.parseInt(prop.getProperty("sub.image.pos.y"));
    }

    if (null != prop.getProperty("qrimage.on.color")) {
      this.onColor = Long.parseLong(prop.getProperty("qrimage.on.color"), 16);
    }

    if (null != prop.getProperty("qrimage.off.color")) {
      this.offColor = Long.parseLong(prop.getProperty("qrimage.off.color"), 16);
    }

    if (null != prop.getProperty("qrimage.width")) {
      this.qrimageWidth = Integer.parseInt(prop.getProperty("qrimage.width"));
    }

    if (null != prop.getProperty("qrimage.height")) {
      this.qrimageHeight = Integer.parseInt(prop.getProperty("qrimage.height"));
    }

    if (null != prop.getProperty("qrimage.pos.x")) {
      this.qrimagePosX = Integer.parseInt(prop.getProperty("qrimage.pos.x"));
    }

    if (null != prop.getProperty("qrimage.pos.y")) {
      this.qrimagePosY = Integer.parseInt(prop.getProperty("qrimage.pos.y"));
    }

    if (null != prop.getProperty("font.name")) {
      this.fontName = prop.getProperty("font.name");
    }

    if (null != prop.getProperty("font.size")) {
      this.fontSize = Integer.parseInt(prop.getProperty("font.size"));
    }

    if (null != prop.getProperty("font.color")) {
      Integer r = Integer.parseInt(prop.getProperty("font.color").split(",")[0]);
      Integer g = Integer.parseInt(prop.getProperty("font.color").split(",")[1]);
      Integer b = Integer.parseInt(prop.getProperty("font.color").split(",")[2]);

      this.fontColor = new Color(r, g, b);
    }

    if (null != prop.getProperty("address.draw.pos.x")) {
      this.addressDrawPosX = Integer.parseInt(prop.getProperty("address.draw.pos.x"));
    }

    if (null != prop.getProperty("address.draw.pos.y")) {
      this.addressDrawPosY = Integer.parseInt(prop.getProperty("address.draw.pos.y"));
    }

    this.config = new MatrixToImageConfig(this.onColor.intValue(), this.offColor.intValue());

    //
    System.out.println(this);
  }

  // public Path getOutPath() {
  // return this.outPath;
  // }

  // public File getBasicImageFile() {
  // return this.basicImageFile;
  // }

  // public File getDataFile() {
  // return this.dataFile;
  // }

  // public Long getOnColor() {
  // return this.onColor;
  // }

  // public Long getOffColor() {
  // return this.offColor;
  // }

  // public MatrixToImageConfig getMatrixToImageConfig() {
  // return this.config;
  // }

  // public Integer getQrimageWidth() {
  // return this.qrimageWidth;
  // }

  // public Integer getQrimageHeight() {
  // return this.qrimageHeight;
  // }

  // public Integer getQrimagePosX() {
  // return this.qrimagePosX;
  // }

  // public Integer getQrimagePosY() {
  // return this.qrimagePosY;
  // }

  // public String getFontName() {
  // return this.fontName;
  // }

  // public Integer getFontSize() {
  // return this.fontSize;
  // }

  // public Color getFontColor() {
  // return this.fontColor;
  // }

  // public Integer getAddressDrawPosX() {
  // return this.addressDrawPosX;
  // }

  // public Integer getAddressDrawPosY() {
  // return this.addressDrawPosY;
  // }

  // public Path getSubImagePath() {
  // return this.subImagePath;
  // }

  // public Integer getSubImagePosX() {
  // return subImagePosX;
  // }

  // public Integer getSubImagePosY() {
  // return subImagePosY;
  // }

  // public List<DataDto> getDataDtos() {
  // return this.dataDtos;
  // }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public List<DataDto> toDataDtos(List<String> list) {
    List<DataDto> dtos = new ArrayList<>();

    String[] arr;
    for (String s : list) {
      arr = s.split("\\^");

      dtos.add(DataDto.builder().pnu(arr[0]).address(arr[1]).amount(Integer.parseInt(arr[2])).build());
    }

    return dtos;
  }

}
