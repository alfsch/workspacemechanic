var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 741, "sl" : 9, "el" : 35, "name" : "ExpectedInvocationAndResults",
    "methods" : [
              {"sl" : 17, "el" : 21, "sc" : 5},  {"sl" : 23, "el" : 25, "sc" : 5},  {"sl" : 27, "el" : 29, "sc" : 5},  {"sl" : 31, "el" : 34, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_408" : {
					  "name" : "nameForStrictMock",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_247" : {
					  "name" : "object",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_109" : {
					  "name" : "floatAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_194" : {
					  "name" : "booleanType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_161" : {
					  "name" : "summarizeSameObjectArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_419" : {
					  "name" : "nameForNiceMock",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_381" : {
					  "name" : "stubBehavior",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_186" : {
					  "name" : "openCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_68" : {
					  "name" : "objectAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_143" : {
					  "name" : "testFind",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_259" : {
					  "name" : "defaultResetToNice",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },]
					  },
		"test_185" : {
					  "name" : "message",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_318" : {
					  "name" : "varargBooleanAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_401" : {
					  "name" : "constraints",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_50" : {
					  "name" : "returnShort",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_296" : {
					  "name" : "defaultBehavior",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_99" : {
					  "name" : "openCallCountByLastCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_291" : {
					  "name" : "correctNumberOfCalls",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_164" : {
					  "name" : "equalsWithDelta",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_85" : {
					  "name" : "exactCallCountByLastCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_123" : {
					  "name" : "booleanAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_206" : {
					  "name" : "answer",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_273" : {
					  "name" : "defaultReturnValueBoolean",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_188" : {
					  "name" : "openCallCountByLastCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_322" : {
					  "name" : "callbackGetsArgumentsEvenIfAMockCallsAnother",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_461" : {
					  "name" : "throwAfterThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_455" : {
					  "name" : "greaterOrEqualOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_449" : {
					  "name" : "moreThanOneResultAndOpenCallCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_215" : {
					  "name" : "exactCallCountByLastCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_297" : {
					  "name" : "tooManyCalls",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_191" : {
					  "name" : "throwSubclassOfCheckedException",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_266" : {
					  "name" : "defaultReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_149" : {
					  "name" : "testStartsWith",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_319" : {
					  "name" : "zeroOrMoreThreeCalls",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_210" : {
					  "name" : "stubAnswer",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_323" : {
					  "name" : "varargDoubleAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_321" : {
					  "name" : "callback",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_168" : {
					  "name" : "booleanAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_131" : {
					  "name" : "longAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_404" : {
					  "name" : "nameForMock",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_436" : {
					  "name" : "compareWithComparator",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_326" : {
					  "name" : "varargLongAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_299" : {
					  "name" : "throwableAndDefaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_14" : {
					  "name" : "argumentsOrdered",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_147" : {
					  "name" : "testThreadSafe",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_207" : {
					  "name" : "twoReturns",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_378" : {
					  "name" : "overloading",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_156" : {
					  "name" : "testContains",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_56" : {
					  "name" : "any",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_320" : {
					  "name" : "varargFloatAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_261" : {
					  "name" : "booleanAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_392" : {
					  "name" : "andOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_310" : {
					  "name" : "varargObjectAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_190" : {
					  "name" : "exactCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_231" : {
					  "name" : "niceToStrict",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_271" : {
					  "name" : "addDocument",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_312" : {
					  "name" : "varargCharAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_282" : {
					  "name" : "mockWithOneExpectedCallPassesWithOneCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_303" : {
					  "name" : "range",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_316" : {
					  "name" : "unorderedCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_110" : {
					  "name" : "floatType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_292" : {
					  "name" : "differentMethods",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_158" : {
					  "name" : "wrongArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_361" : {
					  "name" : "testPrimitive",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_46" : {
					  "name" : "booleanType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_129" : {
					  "name" : "floatAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_202" : {
					  "name" : "moreThanOneArgument",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_433" : {
					  "name" : "defaultMatcherSetTooLate",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_280" : {
					  "name" : "stub",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_192" : {
					  "name" : "wrongArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_232" : {
					  "name" : "objectAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_253" : {
					  "name" : "argumentsOrdered",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_87" : {
					  "name" : "doubleAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_289" : {
					  "name" : "defaultMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_252" : {
					  "name" : "longAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_386" : {
					  "name" : "orOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_226" : {
					  "name" : "longAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_400" : {
					  "name" : "tooFewCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_182" : {
					  "name" : "doubleAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_251" : {
					  "name" : "settingTheSameMatcherIsOk",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },]
					  },
		"test_209" : {
					  "name" : "atLeastTwoReturns",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_305" : {
					  "name" : "noUpperLimitWithoutCallCountSet",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_264" : {
					  "name" : "objectAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_235" : {
					  "name" : "returnLong",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_121" : {
					  "name" : "doubleType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_82" : {
					  "name" : "doubleAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_364" : {
					  "name" : "differentConstraintsOnSameCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_346" : {
					  "name" : "zeroOrMoreNoCalls",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_313" : {
					  "name" : "tooManyCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_173" : {
					  "name" : "longAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_181" : {
					  "name" : "moreThanOneArgument",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_278" : {
					  "name" : "defaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_104" : {
					  "name" : "doubleAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_236" : {
					  "name" : "twoThrows",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_379" : {
					  "name" : "overloading",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_355" : {
					  "name" : "testAnd",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_244" : {
					  "name" : "booleanAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_256" : {
					  "name" : "strictResetToDefault",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_132" : {
					  "name" : "testThreadNotSafe",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_189" : {
					  "name" : "throwCheckedException",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_114" : {
					  "name" : "longType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_233" : {
					  "name" : "expectedArgumentsDelegatedToMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_172" : {
					  "name" : "openCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_295" : {
					  "name" : "tooFewCalls",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_199" : {
					  "name" : "throwSubclassOfError",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_405" : {
					  "name" : "lessThanOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_8" : {
					  "name" : "lessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_443" : {
					  "name" : "nullReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_10" : {
					  "name" : "lessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_342" : {
					  "name" : "assertVerifyNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_275" : {
					  "name" : "stubWithReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_54" : {
					  "name" : "returnByte",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_460" : {
					  "name" : "returnDouble",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_220" : {
					  "name" : "testGenericityFlexibility",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_308" : {
					  "name" : "combination",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_25" : {
					  "name" : "testNull",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_117" : {
					  "name" : "object",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_136" : {
					  "name" : "testMatches",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_255" : {
					  "name" : "doubleAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_445" : {
					  "name" : "voteAgainstRemovals",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_315" : {
					  "name" : "varargByteAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_224" : {
					  "name" : "unorderedCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_317" : {
					  "name" : "orderedCallsSucces",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_258" : {
					  "name" : "floatAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_250" : {
					  "name" : "mixingOrderedAndUnordered",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_248" : {
					  "name" : "errorString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_333" : {
					  "name" : "throwAfterReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_169" : {
					  "name" : "testEndsWith",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_96" : {
					  "name" : "objectAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_444" : {
					  "name" : "cmpTo",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_290" : {
					  "name" : "returnValueAndDefaultReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_254" : {
					  "name" : "booleanAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_242" : {
					  "name" : "returnInt",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_3" : {
					  "name" : "greaterOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_163" : {
					  "name" : "objectAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_145" : {
					  "name" : "testNotNull",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_177" : {
					  "name" : "testSame",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_382" : {
					  "name" : "testPrimitiveVsObject",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_446" : {
					  "name" : "greaterThanOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_249" : {
					  "name" : "doubleType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_197" : {
					  "name" : "throwRuntimeException",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_196" : {
					  "name" : "test",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_265" : {
					  "name" : "voteForRemoval",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_101" : {
					  "name" : "longAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_178" : {
					  "name" : "floatAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_227" : {
					  "name" : "tooManyCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_268" : {
					  "name" : "defaultVoidCallable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_106" : {
					  "name" : "exactCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_417" : {
					  "name" : "nameForMocksControl",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_138" : {
					  "name" : "setOpenCallCountTwice",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_267" : {
					  "name" : "voteAgainstRemoval",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_72" : {
					  "name" : "arrayEquals",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_246" : {
					  "name" : "orderedCallsSucces",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_453" : {
					  "name" : "voteForRemovals",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_228" : {
					  "name" : "tooFewCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_262" : {
					  "name" : "resumeIfFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_238" : {
					  "name" : "returnChar",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_330" : {
					  "name" : "callbackGetsArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_384" : {
					  "name" : "range",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_279" : {
					  "name" : "mockWithOneExpectedCallFailsAtVerify",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_200" : {
					  "name" : "throwError",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_201" : {
					  "name" : "longType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_6" : {
					  "name" : "greaterThan",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_334" : {
					  "name" : "varargShortAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_272" : {
					  "name" : "addAndChangeDocument",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_431" : {
					  "name" : "notOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_440" : {
					  "name" : "returnFloat",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_314" : {
					  "name" : "zeroOrMoreOneCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_203" : {
					  "name" : "floatType",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_92" : {
					  "name" : "booleanAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_19" : {
					  "name" : "testOr",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_293" : {
					  "name" : "mockWithOneExpectedCallFailsAtSecondCall",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_195" : {
					  "name" : "throwSubclassOfRuntimeException",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_376" : {
					  "name" : "testCaptureRightOne",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_44" : {
					  "name" : "floatAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_204" : {
					  "name" : "noUpperLimit",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_327" : {
					  "name" : "varargIntAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_237" : {
					  "name" : "expectedArgumentsDelegatedToMatcher2",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_230" : {
					  "name" : "floatAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_394" : {
					  "name" : "differentMethods",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_269" : {
					  "name" : "unexpectedCallWithArray",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_410" : {
					  "name" : "lessOrEqualOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_229" : {
					  "name" : "doubleAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_134" : {
					  "name" : "objectAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  },
		"test_373" : {
					  "name" : "equalsMissing",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },]
					  },
		"test_193" : {
					  "name" : "summarizeSameObjectArguments",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },{"sl": 31 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },{"sl": 33 },]
					  },
		"test_57" : {
					  "name" : "longAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 23 },{"sl": 27 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 24 },{"sl": 28 },]
					  }
 };

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [  [],   [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 321 , 266 , 440 , 378 , 444 , 134 , 143 , 410 , 264 , 57 , 189 , 8 , 419 , 303 , 312 , 256 , 327 , 220 , 318 , 254 , 443 , 123 , 104 , 404 , 177 , 381 , 199 , 244 , 68 , 267 , 268 , 305 , 232 , 273 , 242 , 271 , 186 , 290 , 330 , 292 , 251 , 203 , 138 , 252 , 92 , 6 , 280 , 361 , 46 , 230 , 291 , 85 , 145 , 342 , 269 , 163 , 161 , 384 , 296 , 54 , 201 , 204 , 132 , 10 , 289 , 308 , 149 , 231 , 401 , 449 , 282 , 158 , 82 , 3 , 236 , 261 , 319 , 99 , 431 , 200 , 446 , 229 , 253 , 195 , 315 , 297 , 408 , 206 , 364 , 147 , 436 , 193 , 19 , 117 , 247 , 188 , 293 , 56 , 278 , 258 , 233 , 326 , 178 , 460 , 355 , 44 , 295 , 185 , 194 , 109 , 192 , 323 , 50 , 101 , 314 , 191 , 265 , 346 , 110 , 255 , 226 , 196 , 181 , 382 , 417 , 250 , 96 , 445 , 169 , 279 , 197 , 25 , 164 , 259 , 190 , 156 , 394 , 322 , 207 , 275 , 386 , 106 , 209 , 455 , 14 , 373 , 237 , 461 , 114 , 238 , 433 , 392 , 405 , 248 , 334 , 453 , 262 , 320 , 172 , 121 , 182 , 136 , 210 , 376 , 129 , 249 , 202 , 310 , 235 , 173 , 299 , 87 , 272 , 72 , 215 , 168 , 379 , 131 , 333   ] ,
  [  ] ,
  [ 321 , 266 , 440 , 378 , 444 , 134 , 143 , 410 , 264 , 57 , 189 , 8 , 419 , 303 , 312 , 256 , 327 , 220 , 318 , 254 , 443 , 123 , 104 , 404 , 177 , 381 , 199 , 244 , 68 , 267 , 268 , 305 , 232 , 273 , 242 , 271 , 186 , 290 , 330 , 292 , 251 , 203 , 138 , 252 , 92 , 6 , 280 , 361 , 46 , 230 , 291 , 85 , 145 , 342 , 269 , 163 , 161 , 384 , 296 , 54 , 201 , 204 , 132 , 10 , 289 , 308 , 149 , 231 , 401 , 449 , 282 , 158 , 82 , 3 , 236 , 261 , 319 , 99 , 431 , 200 , 446 , 229 , 253 , 195 , 315 , 297 , 408 , 206 , 364 , 147 , 436 , 193 , 19 , 117 , 247 , 188 , 293 , 56 , 278 , 258 , 233 , 326 , 178 , 460 , 355 , 44 , 295 , 185 , 194 , 109 , 192 , 323 , 50 , 101 , 314 , 191 , 265 , 346 , 110 , 255 , 226 , 196 , 181 , 382 , 417 , 250 , 96 , 445 , 169 , 279 , 197 , 25 , 164 , 259 , 190 , 156 , 394 , 322 , 207 , 275 , 386 , 106 , 209 , 455 , 14 , 373 , 237 , 461 , 114 , 238 , 433 , 392 , 405 , 248 , 334 , 453 , 262 , 320 , 172 , 121 , 182 , 136 , 210 , 376 , 129 , 249 , 202 , 310 , 235 , 173 , 299 , 87 , 272 , 72 , 215 , 168 , 379 , 131 , 333   ] ,
  [ 321 , 266 , 440 , 378 , 444 , 134 , 143 , 410 , 264 , 57 , 189 , 8 , 419 , 303 , 312 , 256 , 327 , 220 , 318 , 254 , 443 , 123 , 104 , 404 , 177 , 381 , 199 , 244 , 68 , 267 , 268 , 305 , 232 , 273 , 242 , 271 , 186 , 290 , 330 , 292 , 251 , 203 , 138 , 252 , 92 , 6 , 280 , 361 , 46 , 230 , 291 , 85 , 145 , 342 , 269 , 163 , 161 , 384 , 296 , 54 , 201 , 204 , 132 , 10 , 289 , 308 , 149 , 231 , 401 , 449 , 282 , 158 , 82 , 3 , 236 , 261 , 319 , 99 , 431 , 200 , 446 , 229 , 253 , 195 , 315 , 297 , 408 , 206 , 364 , 147 , 436 , 193 , 19 , 117 , 247 , 188 , 293 , 56 , 278 , 258 , 233 , 326 , 178 , 460 , 355 , 44 , 295 , 185 , 194 , 109 , 192 , 323 , 50 , 101 , 314 , 191 , 265 , 346 , 110 , 255 , 226 , 196 , 181 , 382 , 417 , 250 , 96 , 445 , 169 , 279 , 197 , 25 , 164 , 259 , 190 , 156 , 394 , 322 , 207 , 275 , 386 , 106 , 209 , 455 , 14 , 373 , 237 , 461 , 114 , 238 , 433 , 392 , 405 , 248 , 334 , 453 , 262 , 320 , 172 , 121 , 182 , 136 , 210 , 376 , 129 , 249 , 202 , 310 , 235 , 173 , 299 , 87 , 272 , 72 , 215 , 168 , 379 , 131 , 333   ] ,
  [  ] ,
  [  ] ,
  [ 321 , 266 , 440 , 378 , 444 , 134 , 143 , 410 , 264 , 57 , 189 , 8 , 303 , 312 , 256 , 327 , 220 , 318 , 254 , 443 , 123 , 104 , 177 , 381 , 199 , 244 , 68 , 267 , 268 , 305 , 232 , 273 , 242 , 227 , 271 , 186 , 290 , 330 , 292 , 203 , 138 , 252 , 92 , 6 , 280 , 317 , 361 , 46 , 230 , 291 , 85 , 145 , 269 , 163 , 161 , 384 , 296 , 54 , 201 , 204 , 132 , 400 , 10 , 289 , 308 , 149 , 231 , 401 , 449 , 282 , 158 , 82 , 3 , 236 , 261 , 319 , 99 , 246 , 431 , 200 , 446 , 229 , 253 , 195 , 315 , 297 , 206 , 364 , 316 , 147 , 436 , 193 , 19 , 117 , 247 , 188 , 293 , 56 , 278 , 258 , 233 , 326 , 178 , 460 , 355 , 44 , 295 , 185 , 194 , 109 , 192 , 323 , 50 , 101 , 314 , 191 , 313 , 265 , 110 , 255 , 226 , 196 , 181 , 382 , 250 , 96 , 445 , 169 , 197 , 25 , 164 , 190 , 156 , 394 , 322 , 207 , 275 , 386 , 106 , 209 , 455 , 14 , 237 , 461 , 114 , 238 , 433 , 392 , 405 , 248 , 334 , 453 , 262 , 320 , 172 , 228 , 121 , 182 , 136 , 210 , 376 , 129 , 224 , 249 , 202 , 310 , 235 , 173 , 299 , 87 , 272 , 72 , 215 , 168 , 379 , 131 , 333   ] ,
  [ 321 , 266 , 440 , 378 , 444 , 134 , 143 , 410 , 264 , 57 , 189 , 8 , 303 , 312 , 256 , 327 , 220 , 318 , 254 , 443 , 123 , 104 , 177 , 381 , 199 , 244 , 68 , 267 , 268 , 305 , 232 , 273 , 242 , 227 , 271 , 186 , 290 , 330 , 292 , 203 , 138 , 252 , 92 , 6 , 280 , 317 , 361 , 46 , 230 , 291 , 85 , 145 , 269 , 163 , 161 , 384 , 296 , 54 , 201 , 204 , 132 , 400 , 10 , 289 , 308 , 149 , 231 , 401 , 449 , 282 , 158 , 82 , 3 , 236 , 261 , 319 , 99 , 246 , 431 , 200 , 446 , 229 , 253 , 195 , 315 , 297 , 206 , 364 , 316 , 147 , 436 , 193 , 19 , 117 , 247 , 188 , 293 , 56 , 278 , 258 , 233 , 326 , 178 , 460 , 355 , 44 , 295 , 185 , 194 , 109 , 192 , 323 , 50 , 101 , 314 , 191 , 313 , 265 , 110 , 255 , 226 , 196 , 181 , 382 , 250 , 96 , 445 , 169 , 197 , 25 , 164 , 190 , 156 , 394 , 322 , 207 , 275 , 386 , 106 , 209 , 455 , 14 , 237 , 461 , 114 , 238 , 433 , 392 , 405 , 248 , 334 , 453 , 262 , 320 , 172 , 228 , 121 , 182 , 136 , 210 , 376 , 129 , 224 , 249 , 202 , 310 , 235 , 173 , 299 , 87 , 272 , 72 , 215 , 168 , 379 , 131 , 333   ] ,
  [  ] ,
  [  ] ,
  [ 321 , 266 , 440 , 378 , 444 , 134 , 143 , 410 , 264 , 57 , 189 , 8 , 419 , 303 , 312 , 256 , 327 , 220 , 318 , 254 , 443 , 123 , 104 , 404 , 177 , 381 , 199 , 244 , 68 , 267 , 268 , 305 , 232 , 273 , 242 , 227 , 271 , 186 , 290 , 330 , 292 , 203 , 138 , 252 , 92 , 6 , 280 , 317 , 361 , 46 , 230 , 291 , 85 , 145 , 342 , 269 , 163 , 161 , 384 , 296 , 54 , 201 , 204 , 132 , 400 , 10 , 289 , 308 , 149 , 231 , 401 , 449 , 282 , 158 , 82 , 3 , 236 , 261 , 319 , 99 , 246 , 431 , 200 , 446 , 229 , 253 , 195 , 315 , 297 , 408 , 206 , 364 , 316 , 147 , 436 , 193 , 19 , 117 , 247 , 188 , 293 , 56 , 278 , 258 , 233 , 326 , 178 , 460 , 355 , 44 , 295 , 185 , 194 , 109 , 192 , 323 , 50 , 101 , 314 , 191 , 313 , 265 , 346 , 110 , 255 , 226 , 196 , 181 , 382 , 417 , 250 , 96 , 445 , 169 , 279 , 197 , 25 , 164 , 190 , 156 , 394 , 322 , 207 , 275 , 386 , 106 , 209 , 455 , 14 , 237 , 461 , 114 , 238 , 433 , 392 , 405 , 248 , 334 , 453 , 262 , 320 , 172 , 228 , 121 , 182 , 136 , 210 , 376 , 129 , 224 , 249 , 202 , 310 , 235 , 173 , 299 , 87 , 272 , 72 , 215 , 168 , 379 , 131 , 333   ] ,
  [ 321 , 266 , 440 , 378 , 444 , 134 , 143 , 410 , 264 , 57 , 189 , 8 , 419 , 303 , 312 , 256 , 327 , 220 , 318 , 254 , 443 , 123 , 104 , 404 , 177 , 381 , 199 , 244 , 68 , 267 , 268 , 305 , 232 , 273 , 242 , 227 , 271 , 186 , 290 , 330 , 292 , 203 , 138 , 252 , 92 , 6 , 280 , 317 , 361 , 46 , 230 , 291 , 85 , 145 , 342 , 269 , 163 , 161 , 384 , 296 , 54 , 201 , 204 , 132 , 400 , 10 , 289 , 308 , 149 , 231 , 401 , 449 , 282 , 158 , 82 , 3 , 236 , 261 , 319 , 99 , 246 , 431 , 200 , 446 , 229 , 253 , 195 , 315 , 297 , 408 , 206 , 364 , 316 , 147 , 436 , 193 , 19 , 117 , 247 , 188 , 293 , 56 , 278 , 258 , 233 , 326 , 178 , 460 , 355 , 44 , 295 , 185 , 194 , 109 , 192 , 323 , 50 , 101 , 314 , 191 , 313 , 265 , 346 , 110 , 255 , 226 , 196 , 181 , 382 , 417 , 250 , 96 , 445 , 169 , 279 , 197 , 25 , 164 , 190 , 156 , 394 , 322 , 207 , 275 , 386 , 106 , 209 , 455 , 14 , 237 , 461 , 114 , 238 , 433 , 392 , 405 , 248 , 334 , 453 , 262 , 320 , 172 , 228 , 121 , 182 , 136 , 210 , 376 , 129 , 224 , 249 , 202 , 310 , 235 , 173 , 299 , 87 , 272 , 72 , 215 , 168 , 379 , 131 , 333   ] ,
  [  ] ,
  [  ] ,
  [ 321 , 266 , 378 , 8 , 419 , 303 , 312 , 327 , 220 , 318 , 404 , 381 , 268 , 305 , 273 , 227 , 290 , 330 , 292 , 6 , 280 , 317 , 85 , 342 , 269 , 161 , 384 , 296 , 400 , 10 , 308 , 231 , 401 , 158 , 3 , 236 , 319 , 246 , 253 , 315 , 297 , 408 , 316 , 436 , 193 , 293 , 233 , 326 , 295 , 185 , 192 , 323 , 314 , 313 , 346 , 181 , 417 , 250 , 279 , 164 , 190 , 394 , 322 , 207 , 275 , 106 , 209 , 14 , 237 , 433 , 248 , 334 , 320 , 228 , 210 , 224 , 202 , 310 , 299 , 215   ] ,
  [  ] ,
  [ 321 , 266 , 378 , 8 , 419 , 303 , 312 , 327 , 220 , 318 , 404 , 381 , 268 , 305 , 273 , 227 , 290 , 330 , 292 , 6 , 280 , 317 , 85 , 342 , 269 , 161 , 384 , 296 , 400 , 10 , 308 , 231 , 401 , 158 , 3 , 236 , 319 , 246 , 253 , 315 , 297 , 408 , 316 , 436 , 193 , 293 , 233 , 326 , 295 , 185 , 192 , 323 , 314 , 313 , 346 , 181 , 417 , 250 , 279 , 164 , 190 , 394 , 322 , 207 , 275 , 106 , 209 , 14 , 237 , 433 , 248 , 334 , 320 , 228 , 210 , 224 , 202 , 310 , 299 , 215   ] ,
  [  ] ,
  [  ] 
];
