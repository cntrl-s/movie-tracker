highlight_row();
function highlight_row() {
    let table = document.getElementById('result-table');
    let cells = table.getElementsByTagName('td');

    for (let i = 0; i < cells.length; i++) {
        // Take each cell
        let cell = cells[i];
        // do something on onclick event for cell
        cell.onclick = function () {
            // Get the row id where the cell exists
            let rowId = this.parentNode.rowIndex;

            let rowSelected = table.getElementsByTagName('tr')[rowId];

            msg = 'Selected movie is : ' + rowSelected.cells[1].innerHTML;
            msg += '(' + rowSelected.cells[2].innerHTML + ')';

            let imdbID = rowSelected.cells[3].innerText;

            let data = "imdb-id=" + encodeURIComponent(imdbID);
            let uriPath = '/save?' + data;

            let xhr = new XMLHttpRequest();
            xhr.open("POST", uriPath, true);
            xhr.onload = function () {
                console.log(this.responseText);
            };
            xhr.send(data);

            alert(msg);
        }
    }
}
