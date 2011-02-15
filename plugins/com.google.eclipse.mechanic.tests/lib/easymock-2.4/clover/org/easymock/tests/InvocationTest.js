var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 683, "sl" : 16, "el" : 87, "name" : "InvocationTest",
    "methods" : [
             {"sl" : 24, "el" : 35, "sc" : 5},  {"sl" : 37, "el" : 43, "sc" : 5},  {"sl" : 45, "el" : 53, "sc" : 5},  {"sl" : 55, "el" : 86, "sc" : 5}  ]}
    ,
    {"id" : 703, "sl" : 58, "el" : 72, "name" : "InvocationTest.ToString",
    "methods" : [
              {"sl" : 61, "el" : 63, "sc" : 13},  {"sl" : 65, "el" : 68, "sc" : 13},  {"sl" : 70, "el" : 71, "sc" : 13}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_306" : {
					  "name" : "testHashCode",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 48 },{"sl": 51 },]
					  },
		"test_309" : {
					  "name" : "testShouldDisplayMocksToStringIfValidJavaIdentifier",
					  "pass" : true ,
					  "methods" : [{"sl": 55 },{"sl": 61 },{"sl": 65 },],
					  "statements" : [{"sl": 62 },{"sl": 67 },{"sl": 74 },{"sl": 75 },{"sl": 78 },{"sl": 81 },{"sl": 84 },]
					  },
		"test_311" : {
					  "name" : "testEquals",
					  "pass" : true ,
					  "methods" : [{"sl": 37 },],
					  "statements" : [{"sl": 39 },{"sl": 40 },{"sl": 41 },{"sl": 42 },]
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
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 311   ] ,
  [  ] ,
  [ 311   ] ,
  [ 311   ] ,
  [ 311   ] ,
  [ 311   ] ,
  [  ] ,
  [  ] ,
  [ 306   ] ,
  [  ] ,
  [ 306   ] ,
  [ 306   ] ,
  [  ] ,
  [  ] ,
  [ 306   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 309   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 309   ] ,
  [ 309   ] ,
  [  ] ,
  [  ] ,
  [ 309   ] ,
  [  ] ,
  [ 309   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 309   ] ,
  [ 309   ] ,
  [  ] ,
  [  ] ,
  [ 309   ] ,
  [  ] ,
  [  ] ,
  [ 309   ] ,
  [  ] ,
  [  ] ,
  [ 309   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
