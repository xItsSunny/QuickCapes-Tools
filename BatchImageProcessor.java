import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class BatchImageProcessor {

    public static BufferedImage processImage(BufferedImage inputImage) {
        int scaleX = inputImage.getWidth() / 22;
        int scaleY = inputImage.getHeight() / 17;

        BufferedImage outputImage = new BufferedImage(
                64 * scaleX,
                32 * scaleY,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, null);
        g2d.dispose();

        return outputImage;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java BatchImageProcessor <folder_path>");
            return;
        }

        File inputDir = new File(args[0]);
        if (!inputDir.isDirectory()) {
            System.out.println("Error: " + args[0] + " is not a directory.");
            return;
        }

        File outputDir = new File(inputDir, "output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        File[] files = inputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
        if (files == null || files.length == 0) {
            System.out.println("No .png files found in " + inputDir.getAbsolutePath());
            return;
        }

        for (File file : files) {
            try {
                BufferedImage input = ImageIO.read(file);
                if (input == null) {
                    System.out.println("Skipping invalid image: " + file.getName());
                    continue;
                }

                BufferedImage output = processImage(input);

                File outputFile = new File(outputDir, file.getName());
                ImageIO.write(output, "png", outputFile);

                System.out.println("Processed: " + file.getName() + " -> " + outputFile.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Failed to process: " + file.getName());
                e.printStackTrace();
            }
        }

        System.out.println("All images processed. Output folder: " + outputDir.getAbsolutePath());
    }
}
