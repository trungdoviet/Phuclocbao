package vn.com.phuclocbao.util;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;


public class MatcherUtil {
	public static Matcher<String> hasValue(final String expectedMessage) {
		  return new TypeSafeMatcher<String>() {
		    @Override
		    public void describeTo(final Description description) {
		      description.appendText("should has text ").appendValue(expectedMessage);
		    }

			@Override
			public boolean matchesSafely(String arg0) {
				return arg0.equals(expectedMessage);
			}

		  };
	}
	
	public static Matcher<Boolean> hasValue(final Boolean expectedMessage) {
		 return new org.hamcrest.TypeSafeMatcher<Boolean>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("should has text ").appendValue(expectedMessage);
			}

			@Override
			protected boolean matchesSafely(Boolean arg0) {
				return arg0.equals(expectedMessage);
			}
		};
	}
	
	public static Matcher<List<Long>> contain(final Long expecteValue) {
		  return new TypeSafeMatcher<List<Long>>() {
		    @Override
		    public void describeTo(final Description description) {
		      description.appendText("should contain value ").appendValue(expecteValue);
		    }

			@Override
			public boolean matchesSafely(List<Long> arg0) {
				return arg0.contains(expecteValue);
			}

		  };
	}
	public static Matcher<String> contain(final String expecteValue) {
		  return new TypeSafeMatcher<String>() {
		    @Override
		    public void describeTo(final Description description) {
		      description.appendText("should contain value ").appendValue(expecteValue);
		    }

			@Override
			public boolean matchesSafely(String arg0) {
				return arg0.contains(expecteValue);
			}

		  };
	}
	public static Matcher<Long> hasValue(final Long expecteValue) {
		  return new TypeSafeMatcher<Long>() {
		    @Override
		    public void describeTo(final Description description) {
		      description.appendText("should has value ").appendValue(expecteValue);
		    }

			@Override
			public boolean matchesSafely(Long arg0) {
				return arg0.equals(expecteValue);
			}

		  };
	}
	
	public static Matcher<Integer> hasValue(final Integer expecteValue) {
		  return new TypeSafeMatcher<Integer>() {
		    @Override
		    public void describeTo(final Description description) {
		      description.appendText("should has value ").appendValue(expecteValue);
		    }

			@Override
			public boolean matchesSafely(Integer arg0) {
				return arg0.equals(expecteValue);
			}

		  };
	}
	
	public static Matcher<Collection<?>> notEmptyCollection(final Collection<?> expectedList) {		
		return new TypeSafeMatcher<Collection<?>>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("collection is empty with value: ").appendValue(expectedList);
				
			}

			@Override
			public boolean matchesSafely(Collection<?> expectedList) {
				return CollectionUtils.isNotEmpty(expectedList);
			}
		};
	}
	
	public static Matcher<String> emptyString() {
		  return new TypeSafeMatcher<String>() {
		    @Override
		    public void describeTo(final Description description) {
		      description.appendText("should be empty ");
		    }

			@Override
			public boolean matchesSafely(String arg0) {
				return arg0 == null || arg0.isEmpty();
			}

		  };
	}
}
