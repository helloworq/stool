package com.example.demo.javafx;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.example.demo.javafx.button.ButtonExample;
import com.example.demo.javafx.webview.WebViewExample;

public class ExampleRunnerFromMaven {

    public static void main(String[] args) {
	try {
		WebViewExample.main(args);
//	    String[] params = Arrays.copyOfRange(args, 1, args.length);
//	    Class.forName(args[0]).getMethod("main", String[].class)
//		    .invoke(null, (Object) params);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
