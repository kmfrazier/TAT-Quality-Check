package com.software.TALL.TATHeaderChecker;

import com.software.TALL.TATHeaderChecker.utils.Globals;
import com.software.TALL.TATHeaderChecker.utils.SaUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("software.tall")
@SpringBootApplication
public class Process {

	public static void main(String[] args) {
		System.out.println("run Process.main()");
		ConfigurableApplicationContext context = SpringApplication.run(Process.class, args);

		runSaAuth();
	}

	private static void runSaAuth() {
		System.out.println("runSaAuth");
		SaUtils.runSaUtils();
	}
	/*private static void processBatchLocales(String batch) {
		System.out.println("Processing batch " + batch);
		try {
			LocaleDao localeDao = new LocaleDao();
			Integer batchNumber = Integer.parseInt(batch);
			ArrayList<Locale> locales = localeDao.getLocalesByBatch(batchNumber);
			for (Locale l : locales) {
				startLocaleThread(l);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void startLocaleThread(Locale locale) {
		Runnable importProcessThread = new PullSheetProcess(locale);
		Thread t = new Thread(importProcessThread, ("tat-header-checker-" + locale.locale));
		t.start();
	}
	*/
	private static void printFinalResults() {

	}

}
