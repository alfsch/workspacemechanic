var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 713, "sl" : 14, "el" : 83, "name" : "StacktraceTest",
    "methods" : [
             {"sl" : 20, "el" : 24, "sc" : 5},  {"sl" : 33, "el" : 43, "sc" : 5},  {"sl" : 45, "el" : 55, "sc" : 5},  {"sl" : 57, "el" : 67, "sc" : 5},  {"sl" : 69, "el" : 82, "sc" : 5}  ]}
    ,
    {"id" : 716, "sl" : 26, "el" : 31, "name" : "StacktraceTest.ToStringThrowsException",
    "methods" : [
              {"sl" : 27, "el" : 30, "sc" : 9}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_335" : {
					  "name" : "assertRecordStateNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 27 },{"sl": 33 },],
					  "statements" : [{"sl": 29 },{"sl": 35 },{"sl": 36 },{"sl": 37 },{"sl": 39 },]
					  },
		"test_341" : {
					  "name" : "assertReplayStateNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 27 },{"sl": 57 },],
					  "statements" : [{"sl": 29 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 63 },]
					  },
		"test_338" : {
					  "name" : "assertReplayNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 27 },{"sl": 45 },],
					  "statements" : [{"sl": 29 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 51 },]
					  },
		"test_342" : {
					  "name" : "assertVerifyNoFillInStacktraceWhenExceptionNotFromEasyMock",
					  "pass" : true ,
					  "methods" : [{"sl": 27 },{"sl": 69 },],
					  "statements" : [{"sl": 29 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 74 },{"sl": 75 },{"sl": 78 },]
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
  [ 341 , 335 , 338 , 342   ] ,
  [  ] ,
  [ 341 , 335 , 338 , 342   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 335   ] ,
  [  ] ,
  [ 335   ] ,
  [ 335   ] ,
  [ 335   ] ,
  [  ] ,
  [ 335   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 338   ] ,
  [  ] ,
  [ 338   ] ,
  [ 338   ] ,
  [ 338   ] ,
  [  ] ,
  [ 338   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 341   ] ,
  [  ] ,
  [ 341   ] ,
  [ 341   ] ,
  [ 341   ] ,
  [  ] ,
  [ 341   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 342   ] ,
  [  ] ,
  [ 342   ] ,
  [ 342   ] ,
  [ 342   ] ,
  [ 342   ] ,
  [ 342   ] ,
  [  ] ,
  [  ] ,
  [ 342   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
