//内存屏幕
function SanbuScreen(cells, startRow, endRow, startCol, endCol) {
    this.cells = cells;
    this.startRow = startRow;
    this.endRow = endRow;
    this.startCol = startCol;
    this.endCol = endCol;
}
function SanbuCell(hasElement, element, location, unit) {
    this.hasElement = hasElement;
    this.element = element;
    this.location = location;
    this.unit = unit;
}
function SanbuElement(id, name, img, pid) {
    this.id = id;
    this.name = name;
    this.img = img;
    this.pid = pid;
}
function SanbuLocation(row, col) {
    this.row = row;
    this.col = col;
}
//创建内存屏幕
function createMMScreen(sourceTree, maxScreenRow, maxScreenCol, unit) {
    //获取tree数据源最大行列
    var curSourceRow = sourceTree.length;
    var curSourceCol = 0;
    for (var ii = 0; ii < curSourceRow; ii++) {
        var length = sourceTree[ii].length;
        if (curSourceCol < length) {
            curSourceCol = length;
        }
    }

    //内存维护最大数组
    var maxMMRow = maxScreenRow;
    var maxMMCol = maxScreenCol;
    if (curSourceRow > maxMMRow) {
        maxMMRow = curSourceRow;
    }
    if (curSourceCol > maxMMCol) {
        maxMMCol = curSourceCol;
    }

    //比较屏幕和当前数据求间隔space
    var spaceRow = 0;
    if (curSourceRow >= maxScreenRow) {
        spaceRow = 0;
    }
    else {
        var n = Math.floor((maxScreenRow - curSourceRow) / curSourceRow);
        spaceRow = n;
    }

    var spaceCol = 0;
    if (curSourceCol >= maxScreenCol) {
        spaceCol = 0;
    }
    else {
        var n = Math.floor((maxScreenCol - curSourceCol) / curSourceCol);
        spaceCol = n;
    }


    var cellArray = new Array();
    for (var i = 0; i < maxMMRow; i++) {
        cellArray[i] = new Array();
        for (var j = 0; j < maxMMCol; j++) {
            var element = new SanbuElement("", "", "", "");
            var location = new SanbuLocation(i, j);
            cellArray[i][j] = new SanbuCell(false, element, location, unit);
        }
        //暂时只处理列
        if (i < sourceTree.length) {
            var jj = 0;
            for (var j = 0; j < sourceTree[i].length; j++) {
                jj = jj + spaceCol;//间距
                var node = sourceTree[i][j];
                var element = new SanbuElement(node.value, node.value, node.symbol, node.parent_id);
                var location = new SanbuLocation(i, j);
                cellArray[i][j] = new SanbuCell(true, element, location, unit);
                jj = jj + spaceCol;//间距
            }
        }
    }
    setCells(cellArray, maxMMRow, maxMMCol, maxScreenRow, maxScreenCol);

    var srent = new SanbuScreen(cellArray, 0, 0 + maxMMRow, 0, 0 + maxMMCol);
    return srent;
}

function getWordCount(cells, maxRow, maxCol) {
    var obj = {};
    for (var i = 0; i < maxRow; i++) {
        for (var j = 0; j < maxCol; j++) {
            var node = cells[i][j].element;

            var item = node.id;
            obj[item] = (obj[item] + 1) || 1;
        }
    }
    return obj;
}
function setCells(cells, maxRow, maxCol, maxScreenRow, maxScreenCol) {
    var countArray = getWordCount(cells, maxRow, maxCol);
    for (var j = 0; j < maxCol; j++) {
        for (var i = 0; i < maxRow; i++) {

            var hasElement = cells[i][j].hasElement;
            var node = cells[i][j].element;
            if (hasElement) {


                var item = node.id;

                var count = countArray[item];
                for (var kk = 0; kk < count; kk++) {

                    if (count > maxScreenRow) {
                        //newArray[ii][jj+parseInt(maxScreenRow/2)]=v;
                        if (kk != parseInt(maxScreenRow / 2)) {

                            cells[i + kk][j].element = new SanbuElement("", "", "", "");
                            cells[i + kk][j].hasElement = false;
                        }
                    }
                    else {
                        if (kk != parseInt(count / 2)) {

                            cells[i + kk][j].element = new SanbuElement("", "", "", "");
                            cells[i + kk][j].hasElement = false;
                        }
                    }



                }
                i = i + count - 1;
            }
        }
    }

}
function node(scene, text, x, y, img, pid) {
    var node = new JTopo.Node(text);

    node.setImage(img, false);
    node.setLocation(x, y);
    node.setSize(40, 40);
    scene.add(node);

    return node;
}

function linkNode(scene, nodeA, nodeZ) {
    var link = new JTopo.CurveLink(nodeA, nodeZ);
    link.strokeColor = '204,204,204';
    link.lineWidth = 1;

    scene.add(link);
    return link;
}
function findNode(nodes, id) {
    for (var ii = 0; ii < nodes.length; ii++) {
        if (nodes[ii].text == id) {
            return nodes[ii];
        }
    }
    return null;
}
function init(stage, scene, rowy, colx, cells, cur_unit, maxRow, maxCol) {
    var nodes = [];

    var useCells = [];
    for (var i = 0 + rowy; i < maxRow + rowy; i++) {
        var row = cells[i];
        for (var j = 0 + colx; j < maxCol + colx; j++) {

            //行列
            if (cells[i][j].hasElement) {
                var nd = node(scene, cells[i][j].element.id,  (j - colx) * cur_unit, (i - rowy) * cur_unit, cells[i][j].element.img, cells[i][j].element.pid);
                nodes.push(nd);
                useCells.push(cells[i][j].element);
            }

        }
    }
    for (var ii = 0; ii < useCells.length; ii++) {
        var cell = useCells[ii];
        var nodeA = findNode(nodes, cell.pid);
        var nodeZ = findNode(nodes, cell.id);
        if (nodeA != null && nodeZ != null) {
            linkNode(scene, nodeA, nodeZ);
        }

    }

   
}
