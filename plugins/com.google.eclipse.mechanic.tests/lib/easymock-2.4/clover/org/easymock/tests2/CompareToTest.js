var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4538, "sl" : 14, "el" : 79, "name" : "CompareToTest",
    "methods" : [
              {"sl" : 16, "el" : 36, "sc" : 5},  {"sl" : 22, "el" : 25, "sc" : 13},  {"sl" : 27, "el" : 31, "sc" : 13},  {"sl" : 37, "el" : 40, "sc" : 5},  {"sl" : 42, "el" : 45, "sc" : 5},  {"sl" : 47, "el" : 50, "sc" : 5},  {"sl" : 52, "el" : 55, "sc" : 5},  {"sl" : 57, "el" : 66, "sc" : 5},  {"sl" : 68, "el" : 78, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_89" : {
					  "name" : "testNotComparable",
					  "pass" : true ,
					  "methods" : [{"sl": 16 },],
					  "statements" : [{"sl": 18 },{"sl": 35 },]
					  },
		"test_174" : {
					  "name" : "testCompareEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 57 },{"sl": 68 },],
					  "statements" : [{"sl": 59 },{"sl": 63 },{"sl": 65 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 75 },{"sl": 76 },{"sl": 77 },]
					  },
		"test_184" : {
					  "name" : "testGreateThan",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },{"sl": 68 },],
					  "statements" : [{"sl": 44 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 75 },{"sl": 76 },{"sl": 77 },]
					  },
		"test_183" : {
					  "name" : "testLessOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 47 },{"sl": 68 },],
					  "statements" : [{"sl": 49 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 75 },{"sl": 76 },{"sl": 77 },]
					  },
		"test_179" : {
					  "name" : "testLessThan",
					  "pass" : true ,
					  "methods" : [{"sl": 37 },{"sl": 68 },],
					  "statements" : [{"sl": 39 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 75 },{"sl": 76 },{"sl": 77 },]
					  },
		"test_176" : {
					  "name" : "testGreateOrEqual",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },{"sl": 68 },],
					  "statements" : [{"sl": 54 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 75 },{"sl": 76 },{"sl": 77 },]
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
  [ 89   ] ,
  [  ] ,
  [ 89   ] ,
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
  [ 89   ] ,
  [  ] ,
  [ 179   ] ,
  [  ] ,
  [ 179   ] ,
  [  ] ,
  [  ] ,
  [ 184   ] ,
  [  ] ,
  [ 184   ] ,
  [  ] ,
  [  ] ,
  [ 183   ] ,
  [  ] ,
  [ 183   ] ,
  [  ] ,
  [  ] ,
  [ 176   ] ,
  [  ] ,
  [ 176   ] ,
  [  ] ,
  [  ] ,
  [ 174   ] ,
  [  ] ,
  [ 174   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 174   ] ,
  [  ] ,
  [ 174   ] ,
  [  ] ,
  [  ] ,
  [ 183 , 184 , 176 , 174 , 179   ] ,
  [  ] ,
  [  ] ,
  [ 183 , 184 , 176 , 174 , 179   ] ,
  [ 183 , 184 , 176 , 174 , 179   ] ,
  [ 183 , 184 , 176 , 174 , 179   ] ,
  [  ] ,
  [ 183 , 184 , 176 , 174 , 179   ] ,
  [ 183 , 184 , 176 , 174 , 179   ] ,
  [ 183 , 184 , 176 , 174 , 179   ] ,
  [  ] ,
  [  ] 
];
