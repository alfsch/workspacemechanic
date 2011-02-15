var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 679, "sl" : 10, "el" : 16, "name" : "Util",
    "methods" : [
              {"sl" : 11, "el" : 15, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_335" : {
					  "name" : "assertRecordStateNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_341" : {
					  "name" : "assertReplayStateNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_400" : {
					  "name" : "tooFewCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_338" : {
					  "name" : "assertReplayNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_342" : {
					  "name" : "assertVerifyNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_228" : {
					  "name" : "tooFewCallsFailure",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_0" : {
					  "name" : "secondCallWithoutReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_4" : {
					  "name" : "activateWithoutReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
					  },
		"test_207" : {
					  "name" : "twoReturns",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },{"sl": 13 },{"sl": 14 },]
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
  [ 341 , 335 , 0 , 4 , 338 , 228 , 207 , 342 , 400   ] ,
  [ 341 , 335 , 0 , 4 , 338 , 228 , 207 , 342 , 400   ] ,
  [ 341 , 335 , 0 , 4 , 338 , 228 , 207 , 342 , 400   ] ,
  [ 341 , 335 , 0 , 4 , 338 , 228 , 207 , 342 , 400   ] ,
  [  ] ,
  [  ] 
];
