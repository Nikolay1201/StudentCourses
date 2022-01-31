

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import service.CRUDTestCase;

public class TestRunner {
	public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(CRUDTestCase.class);
			
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
			
	      System.out.println(result.wasSuccessful());
	}
}
