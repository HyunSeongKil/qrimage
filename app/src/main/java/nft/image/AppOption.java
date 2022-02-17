package nft.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import com.google.zxing.client.j2se.MatrixToImageConfig;


public class AppOption {
  private Path outPath;
  private File basicImageFile;
  private File dataFile;
  private List<String> datas;
  private Long onColor = Long.parseLong("00000000", 16);
  private Long offColor = Long.parseLong("ff000000", 16);
  private MatrixToImageConfig config;
  private Integer qrimageWidth = 100;
  private Integer qrimageHeight = 100;
  private Integer qrimagePosX = 100;
  private Integer qrimagePosY = 100;


  public AppOption(Properties prop) throws IOException {
    if (null != prop.getProperty("data.file")) {
      this.dataFile = new File(prop.getProperty("data.file"));
      this.datas = Files.readAllLines(this.dataFile.toPath());
    }

    if (null != prop.getProperty("basic.image.file")) {
      this.basicImageFile = new File(prop.getProperty("basic.image.file"));
    }

    if (null != prop.getProperty("out.path")) {
      this.outPath = Paths.get(prop.getProperty("out.path"));
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

    this.config = new MatrixToImageConfig(this.onColor.intValue(), this.offColor.intValue());
  }


  public Path getOutPath() {
    return this.outPath;
  }

  public File getBasicImageFile() {
    return this.basicImageFile;
  }

  public File getDataFile() {
    return this.dataFile;
  }

  public List<String> getDatas() {
    return this.datas;
  }

  public Long getOnColor() {
    return this.onColor;
  }

  public Long getOffColor() {
    return this.offColor;
  }

  public MatrixToImageConfig getMatrixToImageConfig() {
    return this.config;
  }

  public Integer getQrimageWidth() {
    return this.qrimageWidth;
  }

  public Integer getQrimageHeight() {
    return this.qrimageHeight;
  }

  public Integer getQrimagePosX() {
    return this.qrimagePosX;
  }

  public Integer getQrimagePosY() {
    return this.qrimagePosY;
  }



}
