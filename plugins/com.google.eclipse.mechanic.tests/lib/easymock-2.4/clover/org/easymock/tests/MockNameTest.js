var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 2246, "sl" : 13, "el" : 24, "name" : "MockNameTest",
    "methods" : [
              {"sl" : 17, "el" : 23, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_34" : {
					  "name" : "defaultName",
					  "pass" : true ,
					  "methods" : [{"sl": 17 },],
					  "statements" : [{"sl": 19 },{"sl": 20 },{"sl": 21 },{"sl": 22 },]
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
  [ 34   ] ,
  [  ] ,
  [ 34   ] ,
  [ 34   ] ,
  [ 34   ] ,
  [ 34   ] ,
  [  ] ,
  [  ] 
];
