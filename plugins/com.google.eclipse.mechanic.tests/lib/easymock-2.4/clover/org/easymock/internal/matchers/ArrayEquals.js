var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 267, "sl" : 10, "el" : 81, "name" : "ArrayEquals",
    "methods" : [
              {"sl" : 14, "el" : 16, "sc" : 5},  {"sl" : 18, "el" : 50, "sc" : 5},  {"sl" : 52, "el" : 58, "sc" : 5},  {"sl" : 60, "el" : 69, "sc" : 5},  {"sl" : 71, "el" : 80, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_289" : {
					  "name" : "defaultMatcher",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 37 },]
					  },
		"test_326" : {
					  "name" : "varargLongAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_211" : {
					  "name" : "objectArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 38 },{"sl": 41 },{"sl": 44 },{"sl": 46 },]
					  },
		"test_216" : {
					  "name" : "charArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 28 },]
					  },
		"test_318" : {
					  "name" : "varargBooleanAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_208" : {
					  "name" : "byteArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 25 },]
					  },
		"test_214" : {
					  "name" : "testToString",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 52 },{"sl": 60 },{"sl": 71 },],
					  "statements" : [{"sl": 15 },{"sl": 53 },{"sl": 54 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 64 },{"sl": 65 },{"sl": 68 },{"sl": 72 },{"sl": 73 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_72" : {
					  "name" : "arrayEquals",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 22 },{"sl": 23 },{"sl": 25 },{"sl": 26 },{"sl": 28 },{"sl": 29 },{"sl": 31 },{"sl": 32 },{"sl": 34 },{"sl": 35 },{"sl": 37 },{"sl": 38 },{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 44 },{"sl": 46 },]
					  },
		"test_320" : {
					  "name" : "varargFloatAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_453" : {
					  "name" : "voteForRemovals",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 38 },{"sl": 41 },{"sl": 44 },{"sl": 46 },]
					  },
		"test_223" : {
					  "name" : "shortArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 38 },{"sl": 41 },{"sl": 43 },]
					  },
		"test_310" : {
					  "name" : "varargObjectAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 73 },]
					  },
		"test_334" : {
					  "name" : "varargShortAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_312" : {
					  "name" : "varargCharAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_218" : {
					  "name" : "floatArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 34 },]
					  },
		"test_212" : {
					  "name" : "nonArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 38 },{"sl": 41 },{"sl": 44 },{"sl": 48 },]
					  },
		"test_187" : {
					  "name" : "toStringMixed",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 52 },{"sl": 60 },{"sl": 71 },],
					  "statements" : [{"sl": 15 },{"sl": 53 },{"sl": 54 },{"sl": 56 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 64 },{"sl": 65 },{"sl": 68 },{"sl": 72 },{"sl": 73 },]
					  },
		"test_327" : {
					  "name" : "varargIntAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_315" : {
					  "name" : "varargByteAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_445" : {
					  "name" : "voteAgainstRemovals",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 38 },{"sl": 41 },{"sl": 44 },{"sl": 46 },]
					  },
		"test_219" : {
					  "name" : "intArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 37 },]
					  },
		"test_269" : {
					  "name" : "unexpectedCallWithArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },{"sl": 52 },{"sl": 60 },{"sl": 71 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 38 },{"sl": 41 },{"sl": 44 },{"sl": 46 },{"sl": 53 },{"sl": 54 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 64 },{"sl": 65 },{"sl": 68 },{"sl": 72 },{"sl": 73 },]
					  },
		"test_433" : {
					  "name" : "defaultMatcherSetTooLate",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },{"sl": 52 },{"sl": 60 },{"sl": 71 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 37 },{"sl": 53 },{"sl": 54 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 64 },{"sl": 65 },{"sl": 68 },{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_323" : {
					  "name" : "varargDoubleAccepted",
					  "pass" : true ,
					  "methods" : [{"sl": 71 },],
					  "statements" : [{"sl": 72 },{"sl": 75 },{"sl": 76 },{"sl": 77 },{"sl": 79 },]
					  },
		"test_205" : {
					  "name" : "booleanArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 22 },]
					  },
		"test_217" : {
					  "name" : "doubleArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 31 },]
					  },
		"test_221" : {
					  "name" : "longArray",
					  "pass" : true ,
					  "methods" : [{"sl": 14 },{"sl": 18 },],
					  "statements" : [{"sl": 15 },{"sl": 19 },{"sl": 20 },{"sl": 23 },{"sl": 26 },{"sl": 29 },{"sl": 32 },{"sl": 35 },{"sl": 38 },{"sl": 40 },]
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
  [ 72 , 187 , 212 , 219 , 214 , 211 , 269 , 221 , 216 , 208 , 218 , 223 , 433 , 453 , 289 , 445 , 217 , 205   ] ,
  [ 72 , 187 , 212 , 219 , 214 , 211 , 269 , 221 , 216 , 208 , 218 , 223 , 433 , 453 , 289 , 445 , 217 , 205   ] ,
  [  ] ,
  [  ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 216 , 208 , 218 , 223 , 433 , 453 , 289 , 445 , 217 , 205   ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 216 , 208 , 218 , 223 , 433 , 453 , 289 , 445 , 217 , 205   ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 216 , 208 , 218 , 223 , 433 , 453 , 289 , 445 , 217 , 205   ] ,
  [  ] ,
  [ 72 , 205   ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 216 , 208 , 218 , 223 , 433 , 453 , 289 , 445 , 217   ] ,
  [  ] ,
  [ 72 , 208   ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 216 , 218 , 223 , 433 , 453 , 289 , 445 , 217   ] ,
  [  ] ,
  [ 72 , 216   ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 218 , 223 , 433 , 453 , 289 , 445 , 217   ] ,
  [  ] ,
  [ 72 , 217   ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 218 , 223 , 433 , 453 , 289 , 445   ] ,
  [  ] ,
  [ 72 , 218   ] ,
  [ 72 , 212 , 219 , 211 , 269 , 221 , 223 , 433 , 453 , 289 , 445   ] ,
  [  ] ,
  [ 72 , 219 , 433 , 289   ] ,
  [ 72 , 212 , 211 , 269 , 221 , 223 , 453 , 445   ] ,
  [  ] ,
  [ 72 , 221   ] ,
  [ 72 , 212 , 211 , 269 , 223 , 453 , 445   ] ,
  [  ] ,
  [ 72 , 223   ] ,
  [ 72 , 212 , 211 , 269 , 453 , 445   ] ,
  [  ] ,
  [ 72 , 211 , 269 , 453 , 445   ] ,
  [  ] ,
  [ 212   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [  ] ,
  [ 187   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [  ] ,
  [  ] ,
  [ 187 , 214 , 269 , 433   ] ,
  [  ] ,
  [  ] ,
  [ 320 , 310 , 187 , 315 , 214 , 323 , 269 , 334 , 312 , 327 , 326 , 433 , 318   ] ,
  [ 320 , 310 , 187 , 315 , 214 , 323 , 269 , 334 , 312 , 327 , 326 , 433 , 318   ] ,
  [ 310 , 187 , 214 , 269   ] ,
  [  ] ,
  [ 320 , 315 , 214 , 323 , 334 , 312 , 327 , 326 , 433 , 318   ] ,
  [ 320 , 315 , 214 , 323 , 334 , 312 , 327 , 326 , 433 , 318   ] ,
  [ 320 , 315 , 214 , 323 , 334 , 312 , 327 , 326 , 433 , 318   ] ,
  [  ] ,
  [ 320 , 315 , 214 , 323 , 334 , 312 , 327 , 326 , 433 , 318   ] ,
  [  ] ,
  [  ] 
];
