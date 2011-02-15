var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2799, "sl" : 11, "el" : 29, "name" : "StartsWith",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 24, "sc" : 5},  {"sl" : 26, "el" : 28, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_149" : {
					  "name" : "testStartsWith",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
					  },
		"test_458" : {
					  "name" : "startsWithToString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 26 },],
					  "statements" : [{"sl": 18 },{"sl": 27 },]
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
  [ 149 , 458   ] ,
  [ 149 , 458   ] ,
  [  ] ,
  [  ] ,
  [ 149   ] ,
  [ 149   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 458   ] ,
  [ 458   ] ,
  [  ] ,
  [  ] 
];
