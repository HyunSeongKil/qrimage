/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package nft.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.checkerframework.checker.units.qual.g;

public class App {
  static AppOption appOption;

  public App() {
  }

  public String getGreeting() {
    return "Hello World!";
  }

  public static void main(String[] args) throws Exception {
    // List<String> lines = Files.readAllLines(Paths.get(args[0]),
    // StandardCharsets.UTF_8);
    // System.out.println(new App().getGreeting());

    // BufferedImage im = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
    // Graphics g = im.createGraphics();

    // //
    // g.setColor(Color.WHITE);
    // g.fillRect(0, 0, 400, 300);

    // //
    // g.setColor(new Color(123456));
    // g.fillOval(0, 0, 100, 100);

    // g.dispose();

    // ImageIO.write(im, "png", new File("c:\\temp\\" + System.nanoTime() +
    // ".png"));

    Options options = createOptions();
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("ant", options);

    Properties prop = loadProperties(cmd);

    //
    App.appOption = new AppOption(prop);

    //
    int i = 0;
    for (DataDto dto : appOption.getDataDtos()) {

      File subImageFile = getSubImageFileByAmount(appOption.getSubImagePath(), dto.getAmount());

      createNftImage(appOption.getBasicImageFile(), subImageFile, dto);

      // //
      // String qrcodeImageFilename = App.createQrcode(message);

      // xxx(message, qrcodeImageFilename, "?????? ????????? ????????? ????????????25?????? 37");

      // System.out.println(i++ + "/" + appOption.getDatas().size() + "\t" + new
      // Date() + "\t" + message);

      // // ???????????? ??????
      // appOption.getOutPath().resolve(qrcodeImageFilename).toFile().delete();

      System.out.println((i++) + "\t???:" + dto.getPnu() + "\t??????:" + subImageFile.getName());
    }
  }

  /**
   * nft ????????? ??????
   * 
   * @param basicImageFile
   * @param subImageFile
   * @param dto
   * @throws IOException
   */
  private static void createNftImage(File basicImageFile, File subImageFile, DataDto dto) throws IOException {
    BufferedImage basicBufferedImage = ImageIO.read(basicImageFile);
    BufferedImage blankBi = createBlankBufferedImage(basicBufferedImage.getWidth(), basicBufferedImage.getHeight());

    Graphics g = blankBi.getGraphics();

    // ??????????????? draw subImage
    drawSubImage(g, basicBufferedImage, appOption);

    // ??????????????? ?????? ??????
    drawAddress(g, dto.getAddress(), appOption);

    g.dispose();

    // ?????? ????????? ????????? ????????? ??????
    writeToFile(blankBi, appOption.getOutPath().resolve(dto.getPnu() + ".png").toFile());
  }

  /**
   * ????????? ?????????
   * 
   * @param g
   * @param bi
   * @param appOption
   */
  private static void drawSubImage(Graphics g, BufferedImage bi, AppOption appOption) {
    g.drawImage(bi, appOption.getSubImagePosX(), appOption.getSubImagePosY(), null);
  }

  /**
   * ????????? ??????
   * 
   * @param g
   * @param text
   * @param appOption
   */
  static void drawAddress(Graphics g, String text, AppOption appOption) {
    g.setColor(appOption.getFontColor());
    g.setFont(new Font(appOption.getFontName(), Font.PLAIN, appOption.getFontSize()));
    g.drawString(text, appOption.getAddressDrawPosX(), appOption.getAddressDrawPosY());
  }

  /**
   * ??????????????? ?????? ????????? ?????? ?????????
   * 
   * @param subImagePath
   * @param money
   * @return
   */
  static File getSubImageFileByAmount(Path subImagePath, Integer money) {
    String filename = "";
    if (10000 > money) {
      filename = "a.png";
    } else {
      filename = "b.png";
    }

    return subImagePath.resolve(filename).toFile();
  }

  /**
   * 
   * @return
   */
  static Options createOptions() {
    Options opt = new Options();

    opt.addOption("prop", true, "M property file's full path ex)c:\\temp\\app.properties");

    return opt;
  }

  /**
   * 
   * @param fileName
   * @param qrcodeImageFilename
   * @throws IOException
   */
  static void xxx(String fileName, String qrcodeImageFilename, String addressName) throws IOException {
    BufferedImage image = ImageIO
        .read(appOption.getOutPath().resolve(appOption.getBasicImageFile().getName()).toFile());
    BufferedImage qrimage = ImageIO.read(appOption.getOutPath().resolve(qrcodeImageFilename).toFile());
    BufferedImage combined = createBlankBufferedImage(Math.max(image.getWidth(), qrimage.getWidth()),
        Math.max(image.getHeight(), qrimage.getHeight()));

    Graphics g = combined.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.drawImage(qrimage, appOption.getQrimagePosX(), appOption.getQrimagePosY(), null);

    g.setColor(appOption.getFontColor());
    g.setFont(new Font(appOption.getFontName(), Font.PLAIN, appOption.getFontSize()));
    g.drawString(addressName, appOption.getAddressDrawPosX(), appOption.getAddressDrawPosY());

    g.dispose();

    writeToFile(combined, fileName + ".png");
  }

  /**
   * 
   * @param w
   * @param h
   * @return
   */
  static BufferedImage createBlankBufferedImage(int w, int h) {
    return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
  }

  /**
   * 
   * @param message
   * @return
   * @throws WriterException
   * @throws IOException
   */
  static String createQrcode(String message) throws WriterException, IOException {
    String str = getEncodedString(message);
    BitMatrix bitMatrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, appOption.getQrimageWidth(),
        appOption.getQrimageHeight());
    String filename = System.nanoTime() + ".png";
    writeToFile(bitMatrix, filename);

    return filename;
  }

  /**
   * 
   * @param str
   * @return
   * @throws UnsupportedEncodingException
   */
  static String getEncodedString(String str) throws UnsupportedEncodingException {
    return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
  }

  /**
   * 
   * @param bitMatrix
   * @param filename
   * @throws IOException
   */
  static void writeToFile(BitMatrix bitMatrix, String filename) throws IOException {
    MatrixToImageWriter.writeToPath(bitMatrix, "png", appOption.getOutPath().resolve(filename),
        appOption.getConfig());
  }

  /**
   * 
   * @param bi
   * @param filename
   * @throws IOException
   */
  static void writeToFile(BufferedImage bi, String filename) throws IOException {
    ImageIO.write(bi, "png", appOption.getOutPath().resolve(filename).toFile());
  }

  public static void writeToFile(BufferedImage bi, File outFile) throws IOException {
    ImageIO.write(bi, "png", outFile);
  }

  /**
   * 
   * @param cmd
   * @return
   * @throws Exception
   */
  private static Properties loadProperties(CommandLine cmd) throws Exception {
    if (!cmd.hasOption("prop")) {
      throw new MissingOptionException("prop");
    }

    try (InputStream is = new FileInputStream(Paths.get(cmd.getOptionValue("prop")).toString())) {
      Properties prop = new Properties();
      prop.load(is);

      return prop;
    } catch (Exception e) {
      throw e;
    }
  }

}
