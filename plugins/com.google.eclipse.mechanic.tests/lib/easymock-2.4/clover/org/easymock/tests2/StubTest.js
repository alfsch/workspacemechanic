var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3772, "sl" : 14, "el" : 72, "name" : "StubTest",
    "methods" : [
              {"sl" : 17, "el" : 20, "sc" : 5},  {"sl" : 22, "el" : 42, "sc" : 5},  {"sl" : 44, "el" : 70, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_280" : {
					  "name" : "stub",
					  "pass" : true ,
					  "methods" : [{"sl": 22 },],
					  "statements" : [{"sl": 24 },{"sl": 25 },{"sl": 26 },{"sl": 27 },{"sl": 28 },{"sl": 29 },{"sl": 31 },{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 36 },{"sl": 37 },{"sl": 38 },{"sl": 40 },]
					  },
		"test_275" : {
					  "name" : "stubWithReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },],
					  "statements" : [{"sl": 46 },{"sl": 47 },{"sl": 50 },{"sl": 52 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 56 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 64 },{"sl": 65 },{"sl": 66 },{"sl": 69 },]
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
  [ 280   ] ,
  [  ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [  ] ,
  [ 280   ] ,
  [  ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [ 280   ] ,
  [  ] ,
  [ 280   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 275   ] ,
  [  ] ,
  [ 275   ] ,
  [ 275   ] ,
  [  ] ,
  [  ] ,
  [ 275   ] ,
  [  ] ,
  [ 275   ] ,
  [ 275   ] ,
  [ 275   ] ,
  [ 275   ] ,
  [ 275   ] ,
  [  ] ,
  [  ] ,
  [ 275   ] ,
  [ 275   ] ,
  [ 275   ] ,
  [  ] ,
  [  ] ,
  [ 275   ] ,
  [ 275   ] ,
  [ 275   ] ,
  [  ] ,
  [  ] ,
  [ 275   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
