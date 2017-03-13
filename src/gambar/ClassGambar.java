/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gambar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author Zidan
 */
public class ClassGambar extends JPanel {
    
// Variabel Global
public ImageIcon SourceIcon;
public Image SourceImage;
public ImageIcon ScaleIcon;
public Image ScaleImage;
public Image ResultImage;
public Image ScaleResultImage;
public ImageIcon ScaleResultIcon;
public String URLImage;
public boolean ScaledFlag=false;
public BufferedImage SourceBuffer;
public BufferedImage ResultBuffer;
public long sWidth;
public long sHeight;
public long newPixels;
    private String img;
    private int newColor;


//Konstruktor
ClassGambar(String Url,long width, long height)
{
    URLImage=Url;
    if(width<=0 || height <=0)
    {
        ScaledFlag=false;
    }
    else
    {
        ScaledFlag=true;
        sWidth=width;
        sHeight=height;
    }
}
public ImageIcon GetIcon()
{
    if(!URLImage.equals(""))
    {
        SourceIcon=new ImageIcon(URLImage);
        SourceImage=SourceIcon.getImage();
        try
        {
            SourceBuffer=ImageIO.read(new File(URLImage));
        }
        catch (IOException x) {
            JOptionPane.showMessageDialog(null, x.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        } 
        System.out.println(SourceIcon.getIconWidth());
        if(ScaledFlag)
        {
            ScaleImage=SourceImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
            ScaleIcon=new ImageIcon(ScaleImage);
            return ScaleIcon;
        }
        else
        {
            return SourceIcon;
        }
    }
    else
    {
        return null;
    }
}
//fungsi Grayscale
public void Grayscale()
{
    ResultBuffer=deepCopy(SourceBuffer);
    long tWidth=ResultBuffer.getWidth();
    long tHeight=ResultBuffer.getHeight();
    long x,y;
    int RGB,Red,Green,Blue,Gray;
    Color tWarna;
    for (x=0;x<tWidth;x++)
    {
        for(y=0;y<tHeight;y++)
        {
           RGB=ResultBuffer.getRGB((int)x,(int)y);
           tWarna=new Color(RGB);
           
           Red=tWarna.getRed();
           Green=tWarna.getGreen();
           Blue=tWarna.getBlue();
           Gray=(Red+Green+Blue)/3;
           tWarna=new Color(Gray,Gray,Gray);
           ResultBuffer.setRGB((int)x,(int)y,tWarna.getRGB());
        }
    }
    
    ResultImage=(Image) ResultBuffer;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}
//fungsi Biner
public void Biner()
{
    ResultBuffer=deepCopy(SourceBuffer);
    long tWidth=ResultBuffer.getWidth();
    long tHeight=ResultBuffer.getHeight();
    long x,y;
    int RGB,Red,Green,Blue,Gray;
    Color tWarna;
    for(x=0;x<tWidth;x++)
    {
        for(y=0;y<tHeight;y++)
        {
            RGB=ResultBuffer.getRGB((int)x,(int)y);
            tWarna=new Color(RGB);
            Red=tWarna.getRed();
            Green=tWarna.getGreen();
            Blue=tWarna.getBlue();
            Gray=(Red+Green+Blue)/3;
            if(Gray<=128)
            {
                Gray=0;
            }
            else
            {
                Gray=225;
            }
            tWarna=new Color(Gray,Gray,Gray);
            ResultBuffer.setRGB((int)x,(int)y,tWarna.getRGB());
        }
    }
    ResultImage=(Image) ResultBuffer;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon = new ImageIcon(ScaleResultImage);
}
//fungsi Default
public void Default()
{
    ResultBuffer=deepCopy(SourceBuffer);
    long tWidth=ResultBuffer.getWidth();
    long tHeight=ResultBuffer.getHeight();
    long x,y;
    int RGB,Red,Green,Blue;
    Color tWarna;
    for(x=0;x<tWidth;x++)
    {
        for(y=0;y<tHeight;y++)
        {
            RGB=ResultBuffer.getRGB((int)x,(int)y);
            tWarna=new Color(RGB);
            Red=tWarna.getRed();
            Green=tWarna.getGreen();
            Blue=tWarna.getBlue();
            
            tWarna=new Color(Red,Green,Blue);
            ResultBuffer.setRGB((int)x,(int)y,tWarna.getRGB());
        }
    }
    ResultImage=(Image)ResultBuffer;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth,(int)sHeight, Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}
static BufferedImage deepCopy(BufferedImage bi)
{
    ColorModel cm=bi.getColorModel();
    boolean isAlphaPremultiplied=cm.isAlphaPremultiplied();
    WritableRaster rester = bi.copyData(null);
    return new BufferedImage(cm,rester,isAlphaPremultiplied,null);
}
public void SaveImage(String url)
{
    File tFile=new File(url);
    System.out.println(url);
    try
    {
        String fileNama = tFile.getCanonicalPath();
        if(!fileNama.endsWith(".jpeg"))
        {
            tFile=new File(fileNama+".jpeg");
        }
        ImageIO.write(ResultBuffer,"jpeg", tFile);
        System.out.println("Sukses");
    }
    catch(IOException x)
    {
        JOptionPane.showMessageDialog(null, x.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
    }
}

public void flipV()
   {
       ResultBuffer=deepCopy(SourceBuffer);
       BufferedImage imge=new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),BufferedImage.TYPE_INT_RGB);
       for(int y=0; y<SourceBuffer.getHeight();y++)
       {
           for(int x=0;x<SourceBuffer.getWidth();x++)
           {
               imge.setRGB(x,y,SourceBuffer.getRGB(x,SourceBuffer.getHeight()-1-y));
           }
       }
       ResultImage=imge;
       ScaleResultImage=ResultImage.getScaledInstance((int)sWidth,(int)sHeight,BufferedImage.SCALE_DEFAULT);
       ScaleResultIcon=new ImageIcon(ScaleResultImage);
   }

public void flipH()
{
    ResultBuffer=deepCopy(SourceBuffer);
    BufferedImage imgg=new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(),BufferedImage.TYPE_INT_RGB);
    for(int y=0;y<SourceBuffer.getHeight();y++)
    {
        for(int x=0;x<SourceBuffer.getWidth();x++)
        {
            imgg.setRGB(x,y, SourceBuffer.getRGB(SourceBuffer.getWidth()-1-x, y));
        }
    }
    ResultImage=imgg;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, BufferedImage.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void zoomin()
{
    ResultBuffer=deepCopy(SourceBuffer);
    ResultImage=(Image) ResultBuffer;
    sWidth = sWidth*2;
    sHeight= sHeight*2;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth,(int)sHeight, Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public Void zoomout()
{
    ResultBuffer=deepCopy(SourceBuffer);
    ResultImage=(Image) ResultBuffer;
    sWidth = sWidth/2;
    sHeight=sHeight/2;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
    return null;
}
//fungsi brightness
    public void Brightness(float input){
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        
        
        for (int x = 0; x < tWidth; x++){
            for (int y = 0; y < tHeight; y++){
                
                Color color = new Color(ResultBuffer.getRGB(x, y));
                
                int r, g, b;
                
                r = colorRange((int) (color.getRed() + input));
                g = colorRange((int) (color.getGreen() + input));
                b = colorRange((int) (color.getBlue() + input));
                
                color = new Color(r, g, b);
                ResultBuffer.setRGB(x, y, color.getRGB());
                
            }
        }
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
}

    private int colorRange(int newColor) {
        if(newColor > 255) {
            newColor = 255;
        } else if (newColor < 0) {
            newColor = 0;
        }
        return newColor;
    }

    void rotasiCitra(double derajat, ImageObserver o) {
        ImageIcon icon = new ImageIcon(SourceBuffer);
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage blankCanvas = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2 = (Graphics2D) blankCanvas.getGraphics();
        g2.rotate(Math.toRadians(derajat), icon.getIconWidth()/2, icon.getIconHeight()/2);
        g2.drawImage(SourceBuffer, 0, 0, o);
        
        
        
        ResultBuffer = blankCanvas;
        
        ScaleResultImage = ResultBuffer.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }
    
    void translasiV() {
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage imgee = new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < SourceBuffer.getHeight(); y++) {
            
            for (int x = 0; x < SourceBuffer.getWidth(); x++) {
                
                imgee.setRGB(x, y, SourceBuffer.getRGB(x, y));
                
            }
        }
        
        ResultImage = imgee;
        ScaleResultImage = ResultImage.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
    }

    void translasiH() {
        ResultBuffer = deepCopy(SourceBuffer);
        long tWidth = ResultBuffer.getWidth();
        long tHeight = ResultBuffer.getHeight();
        
        long x, y;
        int RGB, Green, Blue, Red;
        
        Color tWarna;
        for(x = 0; x < tWidth; x++){
            for(y = 0; y <tHeight; y++) {
                RGB = ResultBuffer.getRGB((int)x, (int)y);
                tWarna = new Color(RGB);
                
                Red = tWarna.getRed();
                Green = tWarna.getGreen();
                Blue = tWarna.getBlue();
                
                tWarna = new Color(Red, Green, Blue);
                
                long temp = x;
                
                if (temp > tWidth) {
                    temp = tWidth;
                }
                if (temp > tHeight) {
                    temp = tHeight;
                }
                
                ResultBuffer.setRGB((int)temp, (int)y, tWarna.getRGB());
                
            }
        }
        
        ResultImage = (Image) ResultBuffer;
        ScaleResultImage = ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
  