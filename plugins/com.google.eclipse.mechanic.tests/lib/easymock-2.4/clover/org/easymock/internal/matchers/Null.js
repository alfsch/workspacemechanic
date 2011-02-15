var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4793, "sl" : 11, "el" : 27, "name" : "Null",
    "methods" : [
              {"sl" : 17, "el" : 18, "sc" : 5},  {"sl" : 20, "el" : 22, "sc" : 5},  {"sl" : 24, "el" : 26, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_364" : {
					  "name" : "differentConstraintsOnSameCall",
					  "pass" : true ,
					  "methods" : [{"sl": 20 },],
					  "statements" : [{"sl": 21 },]
					  },
		"test_409" : {
					  "name" : "nullToString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 24 },],
					  "statements" : [{"sl": 25 },]
					  },
		"test_25" : {
					  "name" : "testNull",
					  "pass" : true ,
					  "methods" : [{"sl": 20 },],
					  "statements" : [{"sl": 21 },]
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
  [ 409   ] ,
  [  ] ,
  [  ] ,
  [ 25 , 364   ] ,
  [ 25 , 364   ] ,
  [  ] ,
  [  ] ,
  [ 409   ] ,
  [ 409   ] ,
  [  ] ,
  [  ] 
];
