var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3864, "sl" : 14, "el" : 54, "name" : "UsageFloatingPointReturnValueTest",
    "methods" : [
              {"sl" : 19, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 38, "sc" : 5},  {"sl" : 40, "el" : 53, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_440" : {
					  "name" : "returnFloat",
					  "pass" : true ,
					  "methods" : [{"sl": 25 },],
					  "statements" : [{"sl": 27 },{"sl": 28 },{"sl": 29 },{"sl": 31 },{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 37 },]
					  },
		"test_460" : {
					  "name" : "returnDouble",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },],
					  "statements" : [{"sl": 42 },{"sl": 43 },{"sl": 44 },{"sl": 46 },{"sl": 48 },{"sl": 49 },{"sl": 50 },{"sl": 52 },]
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
  [ 440   ] ,
  [  ] ,
  [ 440   ] ,
  [ 440   ] ,
  [ 440   ] ,
  [  ] ,
  [ 440   ] ,
  [  ] ,
  [ 440   ] ,
  [ 440   ] ,
  [ 440   ] ,
  [  ] ,
  [ 440   ] ,
  [  ] ,
  [  ] ,
  [ 460   ] ,
  [  ] ,
  [ 460   ] ,
  [ 460   ] ,
  [ 460   ] ,
  [  ] ,
  [ 460   ] ,
  [  ] ,
  [ 460   ] ,
  [ 460   ] ,
  [ 460   ] ,
  [  ] ,
  [ 460   ] ,
  [  ] ,
  [  ] 
];
