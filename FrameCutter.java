import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class FrameCutter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter path to your image: ");
        String imagePath = scanner.nextLine();

        System.out.print("Enter frame width: ");
        int frameWidth = scanner.nextInt();

        System.out.print("Enter frame height: ");
        int frameHeight = scanner.nextInt();

        scanner.nextLine();
        System.out.print("Enter output directory: ");
        String outputDir = scanner.nextLine();

        try {
            BufferedImage spriteSheet = ImageIO.read(new File(imagePath));
            int imgWidth = spriteSheet.getWidth();
            int imgHeight = spriteSheet.getHeight();

            int frameCount = 0;

            for (int y = 0; y + frameHeight <= imgHeight; y += frameHeight) {
                for (int x = 0; x + frameWidth <= imgWidth; x += frameWidth) {
                    BufferedImage frame = spriteSheet.getSubimage(x, y, frameWidth, frameHeight);
                    File output = new File(outputDir, "frame_" + frameCount + ".png");
                    ImageIO.write(frame, "png", output);
                    frameCount++;
                }
            }

            System.out.println("Exported " + frameCount + " frames successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing the image.");
        }

        scanner.close();
    }
}
