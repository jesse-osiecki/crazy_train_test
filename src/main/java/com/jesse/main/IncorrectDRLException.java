//Jesse Osiecki
//Sunday April 14, 2013
package com.jesse.main;

import org.drools.builder.KnowledgeBuilderErrors;

public class IncorrectDRLException extends Exception {
	public IncorrectDRLException(KnowledgeBuilderErrors errors) {
		super(errors.toString());
	}
}
