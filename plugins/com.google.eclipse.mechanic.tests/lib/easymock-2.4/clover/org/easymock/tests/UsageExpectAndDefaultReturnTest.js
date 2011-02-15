var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 0, "sl" : 20, "el" : 76, "name" : "UsageExpectAndDefaultReturnTest",
    "methods" : [
              {"sl" : 25, "el" : 29, "sc" : 5},  {"sl" : 31, "el" : 38, "sc" : 5},  {"sl" : 40, "el" : 47, "sc" : 5},  {"sl" : 49, "el" : 56, "sc" : 5},  {"sl" : 58, "el" : 65, "sc" : 5},  {"sl" : 67, "el" : 74, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_26" : {
					  "name" : "doubleType",
					  "pass" : true ,
					  "methods" : [{"sl": 58 },],
					  "statements" : [{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 64 },]
					  },
		"test_21" : {
					  "name" : "objectType",
					  "pass" : true ,
					  "methods" : [{"sl": 67 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 72 },{"sl": 73 },]
					  },
		"test_12" : {
					  "name" : "longType",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },],
					  "statements" : [{"sl": 42 },{"sl": 43 },{"sl": 44 },{"sl": 45 },{"sl": 46 },]
					  },
		"test_9" : {
					  "name" : "floatType",
					  "pass" : true ,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 52 },{"sl": 53 },{"sl": 54 },{"sl": 55 },]
					  },
		"test_16" : {
					  "name" : "booleanType",
					  "pass" : true ,
					  "methods" : [{"sl": 31 },],
					  "statements" : [{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 36 },{"sl": 37 },]
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
  [ 16   ] ,
  [  ] ,
  [ 16   ] ,
  [ 16   ] ,
  [ 16   ] ,
  [ 16   ] ,
  [ 16   ] ,
  [  ] ,
  [  ] ,
  [ 12   ] ,
  [  ] ,
  [ 12   ] ,
  [ 12   ] ,
  [ 12   ] ,
  [ 12   ] ,
  [ 12   ] ,
  [  ] ,
  [  ] ,
  [ 9   ] ,
  [  ] ,
  [ 9   ] ,
  [ 9   ] ,
  [ 9   ] ,
  [ 9   ] ,
  [ 9   ] ,
  [  ] ,
  [  ] ,
  [ 26   ] ,
  [  ] ,
  [ 26   ] ,
  [ 26   ] ,
  [ 26   ] ,
  [ 26   ] ,
  [ 26   ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [ 21   ] ,
  [ 21   ] ,
  [ 21   ] ,
  [ 21   ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
