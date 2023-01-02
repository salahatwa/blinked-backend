package com.blinked;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import ink.rayin.htmladapter.base.PdfGenerator;
import ink.rayin.htmladapter.base.utils.JsonSchemaValidator;
import ink.rayin.htmladapter.openhtmltopdf.service.PdfBoxGenerator;
import ink.rayin.tools.utils.FileUtil;
import ink.rayin.tools.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class PdfBoxGeneratorOpenhtmltopdfSamples {

	static String DATA_FILE_NAME = "data.json";
	static String TPL_FILE_NAME = "tpl.json";
	static PdfGenerator pdfGenerator;
	static {
		try {
			pdfGenerator = new PdfBoxGenerator();
			pdfGenerator.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * samples generate 样例生成
	 */
	public static void samplesGenerate() throws Exception {
		
		JSONObject jsonData = JSONObject.parseObject("");

		String outputFile = "";
		

		String outputFileClass = ResourceUtil.getResourceAbsolutePathByClassPath("");

		// 生成pdf路径
		// generate pdf path
		outputFile = (outputFile == null || outputFile.equals(""))
				? new File(outputFileClass).getParentFile().getParent() + "/tmp/ex2_sample_" + System.currentTimeMillis() + ".pdf"
				: outputFile;
		
		
//		pdfGenerator.generatePdfFileByTplConfigFile("C:\\Users\\alinma\\Desktop\\ForMyFather\\backend\\Blinked\\src\\main\\resources\\samples\\receipt\\hospital\\element1.html", jsonData, outputFile);
		
		pdfGenerator.generatePdfFileByHtmlAndData("C:\\Users\\alinma\\Desktop\\ForMyFather\\backend\\Blinked\\src\\main\\resources\\samples\\receipt\\profile1\\index.html",null,outputFile);
		log.info("samplesGenerate [CV] end time：");
		
		


	}

}
