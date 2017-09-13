package com.wanfin.fpd.modules.wish.utils;

import org.apache.poi.hwpf.HWPFDocument;  
import org.apache.poi.hwpf.converter.PicturesManager;  
import org.apache.poi.hwpf.converter.WordToHtmlConverter;  
import org.apache.poi.hwpf.usermodel.Picture;  
import org.apache.poi.hwpf.usermodel.PictureType;  
import org.jsoup.Jsoup;   
import org.w3c.dom.Document;  
  


import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
import javax.xml.transform.OutputKeys;  
import javax.xml.transform.Transformer;  
import javax.xml.transform.TransformerException;  
import javax.xml.transform.TransformerFactory;  
import javax.xml.transform.dom.DOMSource;  
import javax.xml.transform.stream.StreamResult;  

import java.io.*;  
import java.util.List;  
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
/** 
 * Created by Carey on 15-2-2. 
 */  
public class WordToHtml {  
  
  
    public static void main(String argv[]) {  
        try {  
            convert2Html("D:\\test","D:\\新建 Microsoft Word 文档.doc","D:\\1.html");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    //输出html文件   
    public static void writeFile(String content, String path) {  
        FileOutputStream fos = null;   
        BufferedWriter bw = null;  
        org.jsoup.nodes.Document doc = Jsoup.parse(content);  
         content=doc.html();  
        try {  
            File file = new File(path);  
            fos = new FileOutputStream(file);  
            bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8")); 
         
            content = content.replaceAll("<body .*?>", "<body style = \"font-family:SimSun;\">");
            content = content.replaceAll("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />", " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> </meta>");
            content = content.replaceAll("<meta content=\"admin\" name=\"author\"/>", " <meta content=\"admin\" name=\"author\"> </meta>");
            content = content.replaceAll("<p", "<p style = \"font-family:SimSun;\"");
            content = content.replaceAll("<span", "<span style = \"font-family:SimSun;\"");

            bw.write(content);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
                if (fos != null)  
                    fos.close();  
            } catch (IOException ie) {  
            }  
        }  
    }  
  
    //word 转 html   
    public static void convert2Html(String batPath,String fileName, String outPutFile)  
            throws TransformerException, IOException,  
            ParserConfigurationException {  
  
        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));  
         //兼容2007 以上版本  
//        XSSFWorkbook  xssfwork=new XSSFWorkbook(new FileInputStream(fileName));  
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(  
                DocumentBuilderFactory.newInstance().newDocumentBuilder()  
                        .newDocument());  
        wordToHtmlConverter.setPicturesManager( new PicturesManager()  
        {  
            public String savePicture( byte[] content,  
                                       PictureType pictureType, String suggestedName,  
                                       float widthInches, float heightInches )  
            {  
                return "xml/"+suggestedName;  
            }  
        } );  
        wordToHtmlConverter.processDocument(wordDocument);  
        //save pictures  
        List pics=wordDocument.getPicturesTable().getAllPictures();  
        if(pics!=null){  
            for(int i=0;i<pics.size();i++){  
                Picture pic = (Picture)pics.get(i);  
                System.out.println();  
                try {  
                    pic.writeImageContent(new FileOutputStream(batPath + pic.suggestFullFileName()));  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        Document htmlDocument = wordToHtmlConverter.getDocument();  
  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        DOMSource domSource = new DOMSource(htmlDocument);  
        StreamResult streamResult = new StreamResult(out);  
  
  
        TransformerFactory tf = TransformerFactory.newInstance();  
        Transformer serializer = tf.newTransformer(); 
        //serializer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "utf-8"); 
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");  
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");  
        serializer.setOutputProperty(OutputKeys.METHOD, "HTML");  
        serializer.transform(domSource, streamResult);  
        out.close(); 
        
        writeFile(new String(out.toString("UTF-8")), outPutFile); 
       // writeFile(new String(out.toByteArray()), outPutFile);  
    }  
    
}  