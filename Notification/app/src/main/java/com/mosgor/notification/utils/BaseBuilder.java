package com.mosgor.notification.utils;

public class BaseBuilder {


	Integer a = 0;
	Integer b = 0;
	Integer c = 0;
	String d = "";

	private BaseBuilder() {

	}

	public static class Builder {

		BaseBuilder baseBuilder;

		public Builder() {
			baseBuilder = new BaseBuilder();
		}

		public Builder setA(Integer a1) {
			baseBuilder.a = a1;
			return this;
		}

		public Builder setB(Integer b1) {
			baseBuilder.b = b1;
			return this;
		}

		public Builder setC(Integer c1) {
			baseBuilder.c = c1;
			return this;
		}

		public Builder setD(String d1) {
			baseBuilder.d = d1;
			return this;
		}

		public BaseBuilder build() {
			return baseBuilder;
		}
	}
}
