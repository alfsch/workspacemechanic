var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 85, "sl" : 7, "el" : 24, "name" : "GreaterOrEqual",
    "methods" : [
              {"sl" : 11, "el" : 13, "sc" : 5},  {"sl" : 15, "el" : 18, "sc" : 5},  {"sl" : 20, "el" : 23, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "greaterOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
					  },
		"test_10" : {
					  "name" : "lessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_455" : {
					  "name" : "greaterOrEqualOverloaded",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
					  },
		"test_401" : {
					  "name" : "constraints",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
					  },
		"test_176" : {
					  "name" : "testGreateOrEqual",
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
  [ 401 , 455 , 176 , 10 , 3   ] ,
  [ 401 , 455 , 176 , 10 , 3   ] ,
  [  ] ,
  [  ] ,
  [ 401 , 176 , 3   ] ,
  [  ] ,
  [ 401 , 176 , 3   ] ,
  [  ] ,
  [  ] ,
  [ 401 , 455 , 176 , 10 , 3   ] ,
  [  ] ,
  [ 401 , 455 , 176 , 10 , 3   ] ,
  [  ] ,
  [  ] 
];
