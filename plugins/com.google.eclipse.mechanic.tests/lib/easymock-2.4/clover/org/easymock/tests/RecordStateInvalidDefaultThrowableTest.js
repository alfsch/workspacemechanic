var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4223, "sl" : 16, "el" : 81, "name" : "RecordStateInvalidDefaultThrowableTest",
    "methods" : [
             {"sl" : 25, "el" : 29, "sc" : 5},  {"sl" : 31, "el" : 41, "sc" : 5},  {"sl" : 43, "el" : 54, "sc" : 5},  {"sl" : 56, "el" : 67, "sc" : 5},  {"sl" : 69, "el" : 79, "sc" : 5}  ]}
    ,
    {"id" : 4223, "sl" : 21, "el" : 23, "name" : "RecordStateInvalidDefaultThrowableTest.CheckedException",
    "methods" : [
              ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_240" : {
					  "name" : "throwNull",
					  "pass" : true ,
					  "methods" : [{"sl": 31 },],
					  "statements" : [{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 38 },]
					  },
		"test_241" : {
					  "name" : "throwWrongCheckedException",
					  "pass" : true ,
					  "methods" : [{"sl": 56 },],
					  "statements" : [{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 63 },]
					  },
		"test_245" : {
					  "name" : "throwCheckedExceptionWhereNoCheckedExceptionIsThrown",
					  "pass" : true ,
					  "methods" : [{"sl": 43 },],
					  "statements" : [{"sl": 45 },{"sl": 46 },{"sl": 47 },{"sl": 50 },]
					  },
		"test_234" : {
					  "name" : "setDefaultThrowableWithoutMethodCall",
					  "pass" : true ,
					  "methods" : [{"sl": 69 },],
					  "statements" : [{"sl": 71 },{"sl": 72 },{"sl": 75 },]
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
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 240   ] ,
  [  ] ,
  [ 240   ] ,
  [ 240   ] ,
  [ 240   ] ,
  [  ] ,
  [  ] ,
  [ 240   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 245   ] ,
  [  ] ,
  [ 245   ] ,
  [ 245   ] ,
  [ 245   ] ,
  [  ] ,
  [  ] ,
  [ 245   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 241   ] ,
  [  ] ,
  [ 241   ] ,
  [ 241   ] ,
  [ 241   ] ,
  [  ] ,
  [  ] ,
  [ 241   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 234   ] ,
  [  ] ,
  [ 234   ] ,
  [ 234   ] ,
  [  ] ,
  [  ] ,
  [ 234   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
