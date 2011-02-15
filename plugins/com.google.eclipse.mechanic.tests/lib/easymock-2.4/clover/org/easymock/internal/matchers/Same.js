var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 557, "sl" : 11, "el" : 40, "name" : "Same",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 31, "sc" : 5},  {"sl" : 33, "el" : 39, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_415" : {
					  "name" : "sameToStringWithObject",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 25 },{"sl": 33 },],
					  "statements" : [{"sl": 18 },{"sl": 26 },{"sl": 27 },{"sl": 28 },{"sl": 29 },{"sl": 30 },{"sl": 34 },{"sl": 36 },]
					  },
		"test_406" : {
					  "name" : "sameToStringWithString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 25 },{"sl": 33 },],
					  "statements" : [{"sl": 18 },{"sl": 26 },{"sl": 27 },{"sl": 28 },{"sl": 29 },{"sl": 30 },{"sl": 34 },{"sl": 35 },]
					  },
		"test_177" : {
					  "name" : "testSame",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
					  },
		"test_414" : {
					  "name" : "sameToStringWithChar",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 25 },{"sl": 33 },],
					  "statements" : [{"sl": 18 },{"sl": 26 },{"sl": 27 },{"sl": 28 },{"sl": 29 },{"sl": 30 },{"sl": 34 },{"sl": 36 },{"sl": 37 },]
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
  [ 414 , 177 , 415 , 406   ] ,
  [ 414 , 177 , 415 , 406   ] ,
  [  ] ,
  [  ] ,
  [ 177   ] ,
  [ 177   ] ,
  [  ] ,
  [  ] ,
  [ 414 , 415 , 406   ] ,
  [ 414 , 415 , 406   ] ,
  [ 414 , 415 , 406   ] ,
  [ 414 , 415 , 406   ] ,
  [ 414 , 415 , 406   ] ,
  [ 414 , 415 , 406   ] ,
  [  ] ,
  [  ] ,
  [ 414 , 415 , 406   ] ,
  [ 414 , 415 , 406   ] ,
  [ 406   ] ,
  [ 414 , 415   ] ,
  [ 414   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
