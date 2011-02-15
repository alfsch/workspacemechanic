var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 233, "sl" : 7, "el" : 24, "name" : "CompareEqual",
    "methods" : [
              {"sl" : 11, "el" : 13, "sc" : 5},  {"sl" : 15, "el" : 18, "sc" : 5},  {"sl" : 20, "el" : 23, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_174" : {
					  "name" : "testCompareEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 15 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 17 },{"sl": 22 },]
					  },
		"test_444" : {
					  "name" : "cmpTo",
					  "pass" : true ,
					  "methods" : [{"sl": 11 },{"sl": 20 },],
					  "statements" : [{"sl": 12 },{"sl": 22 },]
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
  [ 444 , 174   ] ,
  [ 444 , 174   ] ,
  [  ] ,
  [  ] ,
  [ 174   ] ,
  [  ] ,
  [ 174   ] ,
  [  ] ,
  [  ] ,
  [ 444 , 174   ] ,
  [  ] ,
  [ 444 , 174   ] ,
  [  ] ,
  [  ] 
];
