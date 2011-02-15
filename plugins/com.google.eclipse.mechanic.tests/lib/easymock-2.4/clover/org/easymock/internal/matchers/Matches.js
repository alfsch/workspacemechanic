var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 798, "sl" : 11, "el" : 29, "name" : "Matches",
    "methods" : [
              {"sl" : 17, "el" : 19, "sc" : 5},  {"sl" : 21, "el" : 23, "sc" : 5},  {"sl" : 25, "el" : 28, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_136" : {
					  "name" : "testMatches",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 21 },],
					  "statements" : [{"sl": 18 },{"sl": 22 },]
					  },
		"test_442" : {
					  "name" : "matchesToString",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },{"sl": 25 },],
					  "statements" : [{"sl": 18 },{"sl": 26 },]
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
  [ 136 , 442   ] ,
  [ 136 , 442   ] ,
  [  ] ,
  [  ] ,
  [ 136   ] ,
  [ 136   ] ,
  [  ] ,
  [  ] ,
  [ 442   ] ,
  [ 442   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
