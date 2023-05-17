import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend-angular';
  showDocumentsPanel = false;
  showDocumentsCaption = 'Show documents';

  OnClickShowDocuments() : void {
  	  this.showDocumentsPanel = !this.showDocumentsPanel;
  	  if (this.showDocumentsPanel) {
  	  	  this.showDocumentsCaption = 'Hide documents';
  	  } else {
  	  		this.showDocumentsCaption = 'Show documents';
  	  }
  }
}
