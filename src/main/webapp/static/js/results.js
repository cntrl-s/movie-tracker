highlight_row();
function highlight_row() {
    var table = document.getElementById('result-table');
    var cells = table.getElementsByTagName('td');

    for (var i = 0; i < cells.length; i++) {
        // Take each cell
        var cell = cells[i];
        // do something on onclick event for cell
        cell.onclick = function () {
            // Get the row id where the cell exists
            var rowId = this.parentNode.rowIndex;

            var rowSelected = table.getElementsByTagName('tr')[rowId];

            msg = 'Selected movie is : ' + rowSelected.cells[1].innerHTML;
            msg += '(' + rowSelected.cells[2].innerHTML + ')';
            var imdbID = rowSelected.cells[3].innerText;

            var data = "imdb-id=" + encodeURIComponent(imdbID);
            var uriPath = '/save?' + data;

            var xhr = new XMLHttpRequest();
            xhr.open("POST", uriPath, true);
            xhr.onload = function () {
                // TODO send response from servlet
                console.log(this.responseText);
            };
            xhr.send(data);

            alert(msg);
        }
    }

}
