package data.testing;

import data.DataRead;
import data.ImageNoWhite;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageNoWhiteTest {

    public static void main (String [] args) throws IOException {
        ImageNoWhite inw = new ImageNoWhite();

        File f = new File("data/images/caci.png");

        Image noWhite = DataRead.loadImage("data/images/slogo_turtle.jpg");
        noWhite = inw.convert(noWhite);

        File outputfile = new File("slogonowhite.png");
        ImageIO.write(SwingFXUtils.fromFXImage((noWhite), null), "png", outputfile);
    }
}
