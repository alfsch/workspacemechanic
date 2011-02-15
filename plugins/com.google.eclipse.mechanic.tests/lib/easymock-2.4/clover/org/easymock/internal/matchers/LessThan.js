var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 5218, "sl" : 7, "el" : 24, "name" : "LessThan",
    "methods" : [
              {"sl" : 11, "el" : 13, "sc" : 5},  {"sl" : 15, "el" : 18, "sc" : 5},  {"sl" : 20, "el" : 23, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "greaterOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_405" : {
					  "name" : "lessThanOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_10" : {
					  "name" : "lessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
					  },
		"test_263" : {
					  "name" : "additionalMatchersFailAtReplay",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },],
					  "statements" : [{"sl": 12 },]
					  },
		"test_179" : {
					  "name" : "testLessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
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
  [ 3 , 263 , 405 , 10 , 179   ] ,
  [ 3 , 263 , 405 , 10 , 179   ] ,
  [  ] ,
  [  ] ,
  [ 10 , 179   ] ,
  [  ] ,
  [ 10 , 179   ] ,
  [  ] ,
  [  ] ,
  [ 3 , 405 , 10 , 179   ] ,
  [  ] ,
  [ 3 , 405 , 10 , 179   ] ,
  [  ] ,
  [  ] 
];
