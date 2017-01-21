/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiCtrl, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

import {Component, ViewChild} from '@angular/core';
import {ModalComponent} from "../../modal/modal.component";

@Component({
    selector: 'info-modal',
    templateUrl: 'info-modal.component.html'
})
export class InfoModalComponent {

    @ViewChild('modal')
    modal: ModalComponent;

    aptiBookVersion: string = "@project.version@";

    openModal() {
        this.modal.openModal();
    }

}