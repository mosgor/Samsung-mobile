package com.mosgor.journal;

import java.util.ArrayList;
import java.util.Arrays;

public class Class {
	public String number = "";
	public Teacher classTeacher;
	public Learner[] learners;

	public Learner[] getList(){
		return learners;
	}

	public Parent[] getListParents(){
		ArrayList<Parent> parents = new ArrayList<Parent>();
		for (Learner learner: learners) {
			parents.addAll(Arrays.asList(learner.parents));
		}
		return parents.toArray(new Parent[0]);
	}
}
