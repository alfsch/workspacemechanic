var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4802, "sl" : 16, "el" : 81, "name" : "RecordStateInvalidThrowableTest",
    "methods" : [
             {"sl" : 26, "el" : 30, "sc" : 5},  {"sl" : 32, "el" : 42, "sc" : 5},  {"sl" : 44, "el" : 54, "sc" : 5},  {"sl" : 56, "el" : 66, "sc" : 5},  {"sl" : 68, "el" : 80, "sc" : 5}  ]}
    ,
    {"id" : 4802, "sl" : 22, "el" : 24, "name" : "RecordStateInvalidThrowableTest.CheckedException",
    "methods" : [
              ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_459" : {
					  "name" : "throwWrongCheckedException",
					  "pass" : true ,
					  "methods" : [{"sl": 56 },],
					  "statements" : [{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 63 },]
					  },
		"test_461" : {
					  "name" : "throwAfterThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 68 },],
					  "statements" : [{"sl": 70 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 76 },]
					  },
		"test_452" : {
					  "name" : "throwNull",
					  "pass" : true ,
					  "methods" : [{"sl": 32 },],
					  "statements" : [{"sl": 34 },{"sl": 35 },{"sl": 36 },{"sl": 39 },]
					  },
		"test_448" : {
					  "name" : "throwCheckedExceptionWhereNoCheckedExceptionIsThrown",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },],
					  "statements" : [{"sl": 46 },{"sl": 47 },{"sl": 48 },{"sl": 51 },]
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
  [  ] ,
  [ 452   ] ,
  [  ] ,
  [ 452   ] ,
  [ 452   ] ,
  [ 452   ] ,
  [  ] ,
  [  ] ,
  [ 452   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 448   ] ,
  [  ] ,
  [ 448   ] ,
  [ 448   ] ,
  [ 448   ] ,
  [  ] ,
  [  ] ,
  [ 448   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 459   ] ,
  [  ] ,
  [ 459   ] ,
  [ 459   ] ,
  [ 459   ] ,
  [  ] ,
  [  ] ,
  [ 459   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 461   ] ,
  [  ] ,
  [ 461   ] ,
  [ 461   ] ,
  [ 461   ] ,
  [ 461   ] ,
  [  ] ,
  [  ] ,
  [ 461   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
