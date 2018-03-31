import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;

public class Ultimate {
	static int N = 56;
	static int W = 10000;
	static int H = 10000;
	static double R = 0.49;
	boolean[] covered = new boolean[N];
	public BufferedImage currentImage;
	public BufferedImage previousImage;
	public BufferedImage parker1;
	public BufferedImage parker2;
	public BufferedImage output;
	Canvas obs;
	Graphics g;
	File currentFile;
	File outputFile;
	Random random = new Random();
	public Ultimate(){
		try{
			previousImage = ImageIO.read(new File("base.png"));
			parker1 = ImageIO.read(new File("parker1.png"));
			parker2 = ImageIO.read(new File("parker2.png"));
		}catch(Exception ex3){
			System.err.println("Parker is cross.");
			System.exit(1);
		}
		for(int i = 0; i < N; i++){
			covered[i] = false;
		}
		for(int i = 0; i < N; i++){
			int imagenum = random.nextInt(N) + 1;
			while(covered[imagenum - 1] == true){
				imagenum = random.nextInt(N) + 1;
			}
			System.out.println(i + "	" + imagenum);
			covered[imagenum - 1] = true;
			String imagename = "PHOTOS/" + Integer.toString(imagenum);
			try{
				currentFile = new File(imagename + ".png");
				currentImage = ImageIO.read(currentFile);
			}catch(Exception ex1){
				try{
					currentFile = new File(imagename + ".jpg");
					currentImage = ImageIO.read(currentFile);
				}catch(Exception ex2){
					System.err.println("Parker is displeased");
					System.exit(1);
				}
			}
			output = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
			g = output.createGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, W, H);
			int width;
			int height;
			if(previousImage.getWidth() >= previousImage.getHeight()){
				width = (int)(R*W);
				height = (int)(width*previousImage.getHeight()/previousImage.getWidth());
			}else{
				height = (int)(R*H);
				width = (int)(height*previousImage.getWidth()/previousImage.getHeight());
			}
			g.drawImage(previousImage, (int)(0.25*W - 0.5*width), (int)(0.75*H - 0.5*height), width, height, null);
			if(currentImage.getWidth() >= currentImage.getHeight()){
				width = (int)(R*W);
				height = (int)(width*currentImage.getHeight()/currentImage.getWidth());
			}else{
				height = (int)(R*H);
				width = (int)(height*currentImage.getWidth()/currentImage.getHeight());
			}
			g.drawImage(currentImage, (int)(0.25*W - 0.5*width), (int)(0.25*H - 0.5*height), width, height, null);
			g.drawImage(parker1, (int)(0.5*W + 0.5*(0.5 - R)*W), (int)(0.5*(0.5 - R)*H), (int)(R*W), (int)(R*H), null);
			g.drawImage(parker2, (int)(0.5*W) + (int)(0.5*(0.5 - R)*W), (int)(0.5*H + 0.5*(0.5 - R)*H), (int)(R*W), (int)(R*H), null);
			g.dispose();
			previousImage = output;
		}
		outputFile = new File("output.png");
		try{
			ImageIO.write(output, "png", outputFile);
		}catch(Exception ex){
			System.err.println("1/0");
			System.exit(1);
		}
	}
	public static void main(String[] args){
		new Ultimate();
	}
}