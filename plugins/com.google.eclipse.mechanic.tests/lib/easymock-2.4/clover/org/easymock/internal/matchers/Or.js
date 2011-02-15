var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 33, "sl" : 13, "el" : 42, "name" : "Or",
    "methods" : [
              {"sl" : 19, "el" : 21, "sc" : 5},  {"sl" : 23, "el" : 30, "sc" : 5},  {"sl" : 32, "el" : 41, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_386" : {
					  "name" : "orOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 23 },],
					  "statements" : [{"sl": 20 },{"sl": 24 },{"sl": 25 },{"sl": 26 },]
					  },
		"test_19" : {
					  "name" : "testOr",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 23 },],
					  "statements" : [{"sl": 20 },{"sl": 24 },{"sl": 25 },{"sl": 26 },{"sl": 29 },]
					  },
		"test_451" : {
					  "name" : "orToString",
					  "pass" : true ,
					  "methods" : [{"sl": 19 },{"sl": 32 },],
					  "statements" : [{"sl": 20 },{"sl": 33 },{"sl": 34 },{"sl": 35 },{"sl": 36 },{"sl": 37 },{"sl": 40 },]
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
  [ 451 , 19 , 386   ] ,
  [ 451 , 19 , 386   ] ,
  [  ] ,
  [  ] ,
  [ 19 , 386   ] ,
  [ 19 , 386   ] ,
  [ 19 , 386   ] ,
  [ 19 , 386   ] ,
  [  ] ,
  [  ] ,
  [ 19   ] ,
  [  ] ,
  [  ] ,
  [ 451   ] ,
  [ 451   ] ,
  [ 451   ] ,
  [ 451   ] ,
  [ 451   ] ,
  [ 451   ] ,
  [  ] ,
  [  ] ,
  [ 451   ] ,
  [  ] ,
  [  ] 
];
