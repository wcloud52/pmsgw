/** common.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */
layui.define(['layer'], function(exports) {
	"use strict";

    var  query = [{
        nodes: [],
        prepend: 'and'
      }];
	var fuzzyQuery = {
			addEqual: function(field, value, prepend,sign) {
		       
		        var condition = {
		          field: field,
		          value: value,
		          op: '=',
		          sign: sign ? 'yes' : 'no',
		          prepend: prepend ? prepend : 'and'
		        };
		       query[0].nodes.push(condition);
		      },
		      addNotEqual: function(field, value,prepend,sign) {
		         
		          var condition = {
		            field: field,
		            value: value,
		            op: '<>',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addLike: function(field, value,prepend,sign) {
		         
		          var condition = {
		            field: field,
		            value: value,
		            op: 'like',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addIn: function(field, values,prepend,sign) {
		         
		          var condition = {
		            field: field,
		            value: values,
		            op: 'in',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addNotIn: function(field, values,prepend,sign) {
		        
		          var condition = {
		            field: field,
		            value: values,
		            op: 'not in',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addGT: function(field, value,prepend,sign) {
		         
		          var condition = {
		            field: field,
		            value: value,
		            op: '>',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addGTE: function(field, value,prepend,sign) {
		          
		          var condition = {
		            field: field,
		            value: value,
		            op: '>=',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addLT: function(field, value,prepend,sign) {
		         
		          var condition = {
		            field: field,
		            value: value,
		            op: '<',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addLTE: function(field, value,prepend,sign) {
		          
		          var condition = {
		            field: field,
		            value: value,
		            op: '<=',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addBetween: function(field, values,prepend,sign) {
		         
		          /*if (!Array.isArray(values))
		            return;
		          if (values.length < 2)
		            return;*/
		          var condition = {
		            field: field,
		            value: values,
		            op: 'BETWEEN_AND',
		            sign: sign ? 'yes' : 'no',
		            prepend: prepend ? prepend : 'and'
		          };
		          query[0].nodes.push(condition);
		        },
		        addMutiEqual: function(fields, value,sign) {
		         
		          if (!Array.isArray(fields))
		            return;
		          query.push({
		            nodes: [],
		            prepend: 'and'
		          });
		          var current = query[query.length - 1];
		         
		          for(let index in array) 
		          {  
		        	  var field = array[index];
		        	  var condition = {
			                  field: field,
			                  value: value,
			                  op: '=',
			                  sign: sign ? 'yes' : 'no',
			                  prepend: 'or'
			                };
			                current.nodes.push(condition);
		        	
		          }	        	
		         
		        },
		        addMutiLike: function(fields, value) {
		          
		          if (!Array.isArray(fields))
		            return;
		          query.push({
		            nodes: [],
		            prepend: 'and'
		          });
		          var current = query[query.length - 1];
			         
		          for(let index in array) 
		          {  
		        	  var field = array[index];
		        	  var condition = {
			                  field: field,
			                  value: value,
			                  op: 'like',
			                  sign: sign ? 'yes' : 'no',
			                  prepend: 'or'
			                };
			                current.nodes.push(condition);
		        	
		          }
		        },
		        addOrOperates: function(fields, ops, values,sign) {
		         
		         /* if (!Array.isArray(fields) && !Array.isArray(ops) && !Array.isArray(values))
		            return;
		          if (values.length !== fields.length && ops.length !== fields.length)
		            return false;*/
		          query.push({
		            nodes: [],
		            prepend: 'and'
		          });
		          var current = query[query.length - 1];
		          for (var i in fields) {
		            var condition = {
		              field: fields[i],
		              value: values[i],
		              op: ops[i],
		              sign: sign ? 'yes' : 'no',
		              prepend: 'or'
		            };
		            current.nodes.push(condition);
		          }
		        },
		        makeFuzzyQuery: function(n) {
		          switch (n.express) {
		            case 'equal':
		              this.addEqual(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		            case 'notEqual':
		              this.addNotEqual(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		            case 'in':
		              this.addIn(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		            case 'notIn':
		              this.addNotIn(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		            case 'between':
		              this.addBetween(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		            case 'like':
		              this.addLike(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		            case 'little':
		              this.addLT(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		            case 'gt':
		              this.addGT(n.field, n.value, {
		                sign: n.sign,
		                prepend: n.prepend
		              });
		              break;
		          }
		        },
		        clearQuery: function() {
		        	query = [{
		                nodes: [],
		                prepend: 'and'
		              }];
			        },
		      getQuery: function() {
		          if (query[0].nodes.length === 0) {
		            query = query.slice(1);
		          }
		          return query;
		        }
	};

	exports('fuzzyQuery', fuzzyQuery);
});