var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4254, "sl" : 7, "el" : 24, "name" : "GreaterThan",
    "methods" : [
              {"sl" : 11, "el" : 13, "sc" : 5},  {"sl" : 15, "el" : 18, "sc" : 5},  {"sl" : 20, "el" : 23, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "greaterThan",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
					  },
		"test_8" : {
					  "name" : "lessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_446" : {
					  "name" : "greaterThanOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_184" : {
					  "name" : "testGreateThan",
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
  [ 6 , 184 , 446 , 8   ] ,
  [ 6 , 184 , 446 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 184   ] ,
  [  ] ,
  [ 6 , 184   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 184 , 446 , 8   ] ,
  [  ] ,
  [ 6 , 184 , 446 , 8   ] ,
  [  ] ,
  [  ] 
];
