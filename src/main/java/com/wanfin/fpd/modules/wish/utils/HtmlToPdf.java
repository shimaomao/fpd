package com.wanfin.fpd.modules.wish.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

public class HtmlToPdf {
    
	
	
	public static void convertHtmlToPdf(String batPath, String inputFile,
			String outputFile) throws Exception {

		OutputStream os = new FileOutputStream(outputFile);
		ITextRenderer renderer = new ITextRenderer();
		String url = new File(inputFile).toURI().toURL().toString();
		renderer.setDocument(url);

		ITextFontResolver fontResolver = renderer.getFontResolver();
		
		fontResolver.addFont(batPath+"/SIMSUN.TTC", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		/*String sysos = System.getProperties().getProperty("os.name");
		if (sysos.startsWith("win") || sysos.startsWith("Win")) { // 解决中文支持问题
			fontResolver.addFont("C:/Windows/Fonts/SIMSUN.TTC", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED); // 解决图片的相对路径问题
			renderer.getSharedContext().setBaseURL("file:" + batPath);
		} else { //
			// 解决中文支持问题
			fontResolver.addFont("/usr/share/fonts/fontFile/simsun.ttf",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); // 解决图片的相对路径问题
			renderer.getSharedContext().setBaseURL(batPath);
		}*/
        
		renderer.layout();
		renderer.createPDF(os);
		os.flush();
		os.close();
		 
	}

}
