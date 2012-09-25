import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageApp   {

    // Leitura da imagem
    public static BufferedImage loadImage(String surl) {
        BufferedImage bimg = null;
        try {
            URL url = new URL(surl);
            bimg = ImageIO.read(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimg;
    }

    public void apresentaImagem(JFrame frame, BufferedImage img) {
        frame.setBounds(0, 0, img.getWidth(), img.getHeight());
        JImagePanel panel = new JImagePanel(img, 0, 0);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static BufferedImage criaImagemCinza(BufferedImage imgOrigem, int bndOrigem) {
        BufferedImage img = new BufferedImage(256,256, BufferedImage.TYPE_INT_RGB);
        WritableRaster rasterOrigem = imgOrigem.getRaster();
        WritableRaster raster = img.getRaster();
        int controle = 0;
        for(int h=0;h<256;h++)
        {
            for(int w=0;w<256;w++)
            {
                controle = rasterOrigem.getSample(w, h, bndOrigem);
                for (int i=0;i<3;i++)
                {
                	raster.setSample(w, h, i, controle);
                }
            }
        }
        return img;
    }

    public static BufferedImage criaImagemBinaria(BufferedImage imgOrigem) {
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster rasterOrig = imgOrigem.getRaster();
        WritableRaster raster = img.getRaster();
        int controle = 0;
        for(int h=0;h<256;h++)
        {
            for(int w=0;w<256;w++)
            {
            	controle = rasterOrig.getSample(w, h, 0);
                if (controle < 127)
                {
                    raster.setSample(w,h,0,0);
                } else {
                    raster.setSample(w,h,0,1);
                }
            }
        }
        return img;
    }

    public static BufferedImage criaImagemRGB(BufferedImage imgOrigem, String cor) {
        BufferedImage img = new BufferedImage(256,256, BufferedImage.TYPE_INT_RGB);
        WritableRaster rasterOrig = imgOrigem.getRaster();
        WritableRaster raster = img.getRaster();
        int bo = 0;
        int b1 = 0;
        int b2 = 0;
        int b3 = 0;
        if (cor == "red") {
            bo = 0;
            b1 = 0;
            b2 = 1;
            b3 = 2;
        } else if (cor == "green") {
            bo = 1;
            b1 = 1;
            b2 = 0;
            b3 = 2;
        } else if (cor == "blue") {
            bo = 2;
            b1 = 2;
            b2 = 1;
            b3 = 0;
        }
        for(int h=0;h<256;h++)
            for(int w=0;w<256;w++) {
                raster.setSample(w, h, b1, rasterOrig.getSample(w, h, bo));
                raster.setSample(w, h, b2, 0);
                raster.setSample(w, h, b3, 0);
            }
        return img;
    }

    public static void main(String[] args) {
        ImageApp iaCinzaR = new ImageApp();
        ImageApp iaCinzaG = new ImageApp();
        ImageApp iaCinzaB = new ImageApp();
        ImageApp iaR = new ImageApp();
        ImageApp iaG = new ImageApp();
        ImageApp iaB = new ImageApp();
        ImageApp iaBinR = new ImageApp();
        ImageApp iaBinG = new ImageApp();
        ImageApp iaBinB = new ImageApp();
        BufferedImage imgJPEG = loadImage("http://www.inf.ufsc.br/~willrich/smil/midias/imagens/elephant.jpg");

        BufferedImage imgCinzaR = criaImagemCinza(imgJPEG, 0);
        BufferedImage imgCinzaG = criaImagemCinza(imgJPEG, 1);
        BufferedImage imgCinzaB = criaImagemCinza(imgJPEG, 2);
        BufferedImage imgR = criaImagemRGB(imgJPEG, "red");
        BufferedImage imgG = criaImagemRGB(imgJPEG, "green");
        BufferedImage imgB = criaImagemRGB(imgJPEG, "blue");

        BufferedImage imgBinR = criaImagemBinaria(imgCinzaR);
        BufferedImage imgBinG = criaImagemBinaria(imgCinzaG);
        BufferedImage imgBinB = criaImagemBinaria(imgCinzaB);

        JFrame frameCinzaR = new JFrame("Cinza (R)");
        JFrame frameCinzaG = new JFrame("Cinza (G)");
        JFrame frameCinzaB = new JFrame("Cinza (B)");
        JFrame frameR = new JFrame("RGB (R)");
        JFrame frameG = new JFrame("RGB (G)");
        JFrame frameB = new JFrame("RGB (B)");
        JFrame frameBinR = new JFrame("Binaria (R)");
        JFrame frameBinG = new JFrame("Binaria (G)");
        JFrame frameBinB = new JFrame("Binaria (B)");

        iaCinzaR.apresentaImagem(frameCinzaR, imgCinzaR);
        iaCinzaG.apresentaImagem(frameCinzaG, imgCinzaG);
        iaCinzaB.apresentaImagem(frameCinzaB, imgCinzaB);

        iaR.apresentaImagem(frameR, imgR);
        iaG.apresentaImagem(frameG, imgG);
        iaB.apresentaImagem(frameB, imgB);
        iaBinR.apresentaImagem(frameBinR, imgBinR);
        iaBinG.apresentaImagem(frameBinG, imgBinG);
        iaBinB.apresentaImagem(frameBinB, imgBinB);
    }
}
